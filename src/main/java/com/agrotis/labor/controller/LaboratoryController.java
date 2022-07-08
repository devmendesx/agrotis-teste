package com.agrotis.labor.controller;


import com.agrotis.labor.dto.*;
import com.agrotis.labor.service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/laboratory")
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    @GetMapping
    @Cacheable(value = "laboratory_view_all")
    public ResponseEntity<List<LaboratoryViewDto>> findAll() {
        return ResponseEntity.ok(this.laboratoryService.findAll());
    }

    @GetMapping("{cod_laboratory}")
    @Cacheable(value = "laboratory_by_cod")
    public ResponseEntity<LaboratoryViewDto> findByCod(@PathVariable(value = "cod_laboratory") UUID cod){
        return ResponseEntity.ok(this.laboratoryService.findByCodLaboratory(cod));
    }


    @PostMapping
    @CacheEvict(cacheNames = {"laboratory_view_all", "laboratory_by_cod"}, allEntries = true)
    public ResponseEntity<LaboratoryResponseDto> store(@Valid @RequestBody LaboratoryDto labDto) {
        return ResponseEntity.ok(this.laboratoryService.save(labDto));
    }

    @PutMapping
    @CacheEvict(cacheNames = {"laboratory_view_all", "laboratory_by_cod"}, allEntries = true)
    public ResponseEntity<MessageResponseDto> update(@Valid @RequestBody LaboratoryUpdateDto labDto) {
        return ResponseEntity.ok(this.laboratoryService.update(labDto));
    }

    @DeleteMapping
    @CacheEvict(cacheNames = {"laboratory_view_all", "laboratory_by_cod"}, allEntries = true)
    public ResponseEntity<MessageResponseDto> delete(@RequestParam(value = "cod_laboratory") UUID codLaboratory ) {
        return ResponseEntity.ok(this.laboratoryService.delete(codLaboratory));
    }

}
