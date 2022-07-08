package com.agrotis.labor.service;

import com.agrotis.labor.dto.*;
import com.agrotis.labor.entity.Laboratory;
import com.agrotis.labor.repository.LaboratoryRepository;
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
public class LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<LaboratoryViewDto> findAll() {
        try {
            var laboratories = this.laboratoryRepository.findAll();
            return laboratories.stream().map(lab -> LaboratoryViewDto.builder()
                    .id(lab.getId())
                    .name(lab.getName())
                    .codLaboratory(lab.getCodLaboratory()).build()).collect(Collectors.toList());
        } catch (ResponseStatusException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public LaboratoryViewDto findByCodLaboratory(UUID cod) {
        try {
            log.info("c=LaboratoryService, m=findByCodLaboratory, m=Finding a laboratory, cod={}", cod);

            var laboratory = this.laboratoryRepository.findByCodLaboratory(cod);
            if(laboratory.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return LaboratoryViewDto.builder()
                    .id(laboratory.get().getId())
                    .name(laboratory.get().getName())
                    .codLaboratory(laboratory.get().getCodLaboratory())
                    .build();
        } catch (ResponseStatusException exception){
            log.error("c=LaboratoryService, m=findByCodLaboratory, m=Storing a new laboratory, cod={}", cod.toString());
            throw exception;
        }
    }

    @Transactional
    public LaboratoryResponseDto save(LaboratoryDto laboratoryDto) {
        try {
            log.info("c=LaboratoryService, m=save, m=Storing a new laboratory, name={}", laboratoryDto.getName());

            var lab = Laboratory.builder()
                    .codLaboratory(UUID.randomUUID())
                    .name(laboratoryDto.getName())
                    .build();
            var labPersisted = this.laboratoryRepository.save(lab);

            return LaboratoryResponseDto.builder().codLaboratory(labPersisted.getCodLaboratory()).build();
        } catch (Exception ex) {
            log.error("c=LaboratoryService, m=save, m=Error on storing, name={}", laboratoryDto.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public MessageResponseDto delete(UUID codLaboratory) {
        try {
            log.info("c=LaboratoryService, m=delete, m=Deleting a laboratory, cod={}", codLaboratory.toString());

            this.validateLaboratory(codLaboratory);
            this.laboratoryRepository.deleteLaboratoryByCodLaboratory(codLaboratory);

            return MessageResponseDto.builder()
                    .message("Laboratório deletado com sucesso!")
                    .build();
        } catch (ResponseStatusException ex) {
            log.error("c=LaboratoryService, m=delete, m=Failed on delete a laboratory, cod={}", codLaboratory.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public MessageResponseDto update(LaboratoryUpdateDto labDto) {
        try {
            log.info("c=LaboratoryService, m=updateName, m=Updating a laboratory, cod={}", labDto.getCod().toString());
            this.validateLaboratory(labDto.getCod());

            var laboratory = this.laboratoryRepository.findByCodLaboratory(labDto.getCod()).get();
            laboratory.setName(labDto.getName());
            this.laboratoryRepository.save(laboratory);

            return MessageResponseDto.builder()
                    .message("Laboratório atualizado com sucesso!")
                    .build();
        } catch (ResponseStatusException ex) {
            log.error("c=LaboratoryService, m=updateName, m=Failed on updating a laboratory, cod={}", labDto.getCod().toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    public void validateLaboratory(UUID codLaboratory) {
        try {
            log.info("c=LaboratoryService, m=validateLaboratory, m=Validating laboratory, cod={}", codLaboratory.toString());
            var laboratory = this.laboratoryRepository.findByCodLaboratory(codLaboratory);
            if (laboratory.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }

}
