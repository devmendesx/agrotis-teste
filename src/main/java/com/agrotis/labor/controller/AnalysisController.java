package com.agrotis.labor.controller;


import com.agrotis.labor.dto.*;
import com.agrotis.labor.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;


    @GetMapping
    @Cacheable(cacheNames = "analysis_view_all")
    public ResponseEntity<List<AnalysisViewDto>> findAll(){
        return ResponseEntity.ok(this.analysisService.findAll());
    }

    @GetMapping("{cod_analysis}")
    @Cacheable("analysis_by_cod")
    public ResponseEntity<AnalysisViewDto> findByCod(@PathVariable(value = "cod_analysis") UUID cod){
        return ResponseEntity.ok(this.analysisService.findByCodAnalysis(cod));
    }

    @PostMapping
    @CacheEvict(cacheNames = {"analysis_view_all", "analysis_by_cod"})
    public ResponseEntity<AnalysisResponseDto> store(@Valid @RequestBody AnalysisDto analysisDto){
        return ResponseEntity.ok(this.analysisService.save(analysisDto));
    }

    @DeleteMapping
    @CacheEvict(cacheNames = {"analysis_view_all", "analysis_by_cod"})
    public ResponseEntity<MessageResponseDto> delete(@RequestParam(value = "cod_analysis") UUID cod){
        return ResponseEntity.ok(this.analysisService.delete(cod));
    }

    @PutMapping
    @CacheEvict(cacheNames = {"analysis_view_all", "analysis_by_cod"})
    public ResponseEntity<MessageResponseDto> update(@Valid @RequestBody AnalysisUpdateDto analysisUpdateDto){
        return ResponseEntity.ok(this.analysisService.update(analysisUpdateDto));
    }

}
