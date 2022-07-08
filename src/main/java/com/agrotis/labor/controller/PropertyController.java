package com.agrotis.labor.controller;


import com.agrotis.labor.dto.*;
import com.agrotis.labor.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    @Cacheable(value = "property_view_all")
    public ResponseEntity<List<PropertyViewDto>> findAll() {
        return ResponseEntity.ok(this.propertyService.findAll());
    }

    @GetMapping("{cod_property}")
    @Cacheable(value = "property_by_cod")
    public ResponseEntity<PropertyViewDto> findByCod(@PathVariable(value = "cod_property") UUID cod){
        return ResponseEntity.ok(this.propertyService.findByCodProperty(cod));
    }

    @GetMapping("{cnpj}")
    @Cacheable(value = "property_by_cnpj")
    public ResponseEntity<PropertyViewDto> findByCnpj(@PathVariable(value = "cnpj") String cnpj){
        return ResponseEntity.ok(this.propertyService.findByCnpj(cnpj));
    }

    @PostMapping
    @CacheEvict(cacheNames = {"property_view_all", "property_by_cod", "property_by_cnpj"}, allEntries = true)
    public ResponseEntity<PropertyResponseDto> store(@Valid @RequestBody PropertyDto propDto) {
        return ResponseEntity.ok(this.propertyService.save(propDto));
    }

    @PutMapping
    @CacheEvict(cacheNames = {"property_view_all", "property_by_cod", "property_by_cnpj"}, allEntries = true)
    public ResponseEntity<MessageResponseDto> update(@Valid @RequestBody PropertyUpdateDto propDto) {
        return ResponseEntity.ok(this.propertyService.update(propDto));
    }

    @DeleteMapping
    @CacheEvict(cacheNames = {"property_view_all", "property_by_cod", "property_by_cnpj"}, allEntries = true)
    public ResponseEntity<MessageResponseDto> delete(@RequestParam(value = "cod_property") UUID codProperty ) {
        return ResponseEntity.ok(this.propertyService.delete(codProperty));
    }

}
