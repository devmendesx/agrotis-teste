package com.agrotis.labor.service;

import com.agrotis.labor.dto.*;
import com.agrotis.labor.entity.Property;
import com.agrotis.labor.repository.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<PropertyViewDto> findAll() {
        try {
            var property = this.propertyRepository.findAll();
            return property.stream().map(prop -> PropertyViewDto.builder()
                    .id(prop.getId())
                    .name(prop.getName())
                    .codProperty(prop.getCodProperty())
                    .cnpj(prop.getCnpj()).build()).collect(Collectors.toList());
        } catch (ResponseStatusException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public PropertyViewDto findByCodProperty(UUID cod) {
        try {
            log.info("c=PropertyService, m=findByCodProperty, m=Finding a property, cod={}", cod);

            var property = this.propertyRepository.findByCodProperty(cod);
            if (property.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return PropertyViewDto.builder()
                    .id(property.get().getId())
                    .name(property.get().getName())
                    .codProperty(property.get().getCodProperty())
                    .cnpj(property.get().getCnpj())
                    .build();
        } catch (ResponseStatusException exception) {
            log.error("c=LaboratoryService, m=findByCodLaboratory, m=Storing a new laboratory, cod={}", cod.toString());
            throw exception;
        }
    }

    public PropertyViewDto findByCnpj(String propertyCnpj) {
        try {
            var property = this.propertyRepository.findByCnpj(propertyCnpj);
            if(property.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return PropertyViewDto.builder()
                    .id(property.get().getId())
                    .name(property.get().getName())
                    .codProperty(property.get().getCodProperty())
                    .cnpj(property.get().getCnpj())
                    .build();
        } catch (ResponseStatusException ex) {
            log.error("c=PropertyService, m=findByCnpj, m=Error on finding a property, cnpj={}", propertyCnpj);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public PropertyResponseDto save(PropertyDto propDto) {
        try {
            log.info("c=PropertyService, m=save, m=Storing a new property, name={}", propDto.getName());

            var prop = Property.builder()
                    .name(propDto.getName())
                    .codProperty(UUID.randomUUID())
                    .cnpj(propDto.getCnpj()).build();

            this.validadeIfCnpjAlreadyExistsOnSave(prop.getCnpj());
            var propPersisted = this.propertyRepository.save(prop);
            return PropertyResponseDto.builder().codProperty(propPersisted.getCodProperty()).build();
        } catch (Exception ex) {
            log.error("c=PropertyService, m=save, m=Error on storing a property, name={}", propDto.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public MessageResponseDto update(PropertyUpdateDto propDto) {
        try {
            log.info("c=PropertyService, m=update, m=Updating a property, cod={}", propDto.getCod().toString());
            this.validateProperty(propDto.getCod());

            var prop = this.propertyRepository.findByCodProperty(propDto.getCod()).get();
            prop.setName(propDto.getName());
            prop.setCnpj(propDto.getCnpj());
            this.propertyRepository.save(prop);

            return MessageResponseDto.builder()
                    .message("Propriedade atualizada com sucesso!")
                    .build();
        } catch (ResponseStatusException ex) {
            log.error("c=PropertyService, m=update, m=Failed on updating a property, cod={}", propDto.getCod().toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public MessageResponseDto delete(UUID codProperty) {
        try {
            log.info("c=PropertyService, m=delete, m=Deleting a property, cod={}", codProperty.toString());

            this.validateProperty(codProperty);
            this.propertyRepository.deletePropertyByCod(codProperty);

            return MessageResponseDto.builder()
                    .message("Propriedade deletada com sucesso!")
                    .build();
        } catch (ResponseStatusException ex) {
            log.error("c=PropertyService, m=delete, m=Failed on delete a property, cod={}", codProperty.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    public void validateProperty(UUID codProperty) {
        try {
            log.info("c=PropertyService, m=validateProperty, m=Validating property, cod={}", codProperty.toString());
            var property = this.propertyRepository.findByCodProperty(codProperty);
            if (property.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }

    public void validadeIfCnpjAlreadyExistsOnSave(String propertyCnpj) {
        try {
            log.info("c=PropertyService, m=validadeIfCnpjAlreadyExistsOnSave, m=Validating cnpj, cnpj={}", propertyCnpj);
            var property = this.propertyRepository.findByCnpj(propertyCnpj);
            if (property.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ResponseStatusException ex) {
            log.info("c=PropertyService, m=validadeIfCnpjAlreadyExistsOnSave, m=CNPJ already exists, cnpj={}", propertyCnpj);
            throw ex;
        }
    }

}
