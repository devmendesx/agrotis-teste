package com.agrotis.labor.controller;


import com.agrotis.labor.dto.LaboratoryListViewDto;
import com.agrotis.labor.service.LaboratoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@RestController
@RequestMapping("/v1/api/laboratory")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LaboratoryController {

    private LaboratoryService laboratoryService;

    @GetMapping
    @Cacheable("laboratory_view_all")
    public ResponseEntity<List<LaboratoryListViewDto>> findAll(){
        return ResponseEntity.ok(laboratoryService.findAll());
    }

}
