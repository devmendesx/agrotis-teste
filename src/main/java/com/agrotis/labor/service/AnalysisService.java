package com.agrotis.labor.service;

import com.agrotis.labor.dto.*;
import com.agrotis.labor.entity.Analysis;
import com.agrotis.labor.repository.AnalysisRepository;
import com.agrotis.labor.repository.LaboratoryRepository;
import com.agrotis.labor.repository.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnalysisService {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;


    public List<AnalysisViewDto> findAll() {
        var analyzes = this.analysisRepository.findAll();
        return analyzes.stream().map(analysis -> {
            var infoLaboratory = this.laboratoryRepository.findById(analysis.getLaboratoryId());
            var infoProperty = this.propertyRepository.findById(analysis.getPropertyId());
            return AnalysisViewDto.builder()
                    .codAnalysis(analysis.getCodAnalysis())
                    .analystName(analysis.getAnalysisMakerName())
                    .initialDate(analysis.getInitialDate())
                    .finalDate(analysis.getFinalDate())
                    .infoLaboratory(InfoLaboratoryDto.builder()
                            .id(infoLaboratory.get().getId())
                            .name(infoLaboratory.get().getName())
                            .build())
                    .infoProperty(InfoPropertyDto.builder()
                            .id(infoProperty.get().getId())
                            .name(infoProperty.get().getName())
                            .cnpj(infoProperty.get().getCnpj())
                            .build())
                    .notes(analysis.getNotes()).build();
        }).collect(Collectors.toList());
    }

    public AnalysisViewDto findByCodAnalysis(UUID cod) {
        try {
            var analysisFound = this.analysisRepository.findByCodAnalysis(cod);
            if (analysisFound.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            var analysis = analysisFound.get();

            var infoLaboratory = this.laboratoryRepository.findById(analysis.getLaboratoryId());
            var infoProperty = this.propertyRepository.findById(analysis.getPropertyId());

            return AnalysisViewDto.builder()
                    .codAnalysis(analysis.getCodAnalysis())
                    .analystName(analysis.getAnalysisMakerName())
                    .initialDate(analysis.getInitialDate())
                    .finalDate(analysis.getFinalDate())
                    .infoLaboratory(InfoLaboratoryDto.builder()
                            .id(infoLaboratory.get().getId())
                            .name(infoLaboratory.get().getName())
                            .build())
                    .infoProperty(InfoPropertyDto.builder()
                            .id(infoProperty.get().getId())
                            .name(infoProperty.get().getName())
                            .cnpj(infoProperty.get().getCnpj())
                            .build())
                    .notes(analysis.getNotes()).build();
        } catch (ResponseStatusException ex) {
            log.info("c=AnalysisService, m=findByCod, m=Error on finding a analysis, cod={}", cod);
            throw ex;
        }
    }

    @Transactional
    public AnalysisResponseDto save(AnalysisDto analysisDto) {
        try {
            log.info("c=AnalysisService, m=save, m=Saving a new analysis, analystName={}", analysisDto.getAnalystName());
            if (validateInitialAndFinalDate(analysisDto.getInitialDate(), analysisDto.getFinalDate())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if (this.validadePropertyAndLaboratoryIds(analysisDto.getInfoProperty().getId(), analysisDto.getInfoLaboratory().getId())
                    && this.validadePropertyAndLaborNames(analysisDto.getInfoProperty(), analysisDto.getInfoLaboratory())
            ) {
                var analysis = Analysis.builder()
                        .codAnalysis(UUID.randomUUID())
                        .analysisMakerName(analysisDto.getAnalystName())
                        .initialDate(analysisDto.getInitialDate())
                        .finalDate(analysisDto.getFinalDate())
                        .laboratoryId(analysisDto.getInfoLaboratory().getId())
                        .propertyId(analysisDto.getInfoProperty().getId())
                        .notes(analysisDto.getNotes()).build();
                var analysisPersisted = this.analysisRepository.save(analysis);

                return AnalysisResponseDto.builder().codAnalysis(analysisPersisted.getCodAnalysis()).build();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ResponseStatusException ex) {
            log.error("c=AnalysisService, m=save, m=Failed on saving a analysis, analystName={}", analysisDto.getAnalystName());
            throw ex;
        }
    }

    @Transactional
    public MessageResponseDto update(AnalysisUpdateDto analysisDto) {
        try {
            log.info("c=AnalysisService, m=update, m=Saving a new analysis, cod={}", analysisDto.getCodAnalysis());

            if (validateInitialAndFinalDate(analysisDto.getInitialDate(), analysisDto.getFinalDate())
            && this.validadePropertyAndLaborNames(analysisDto.getInfoProperty(), analysisDto.getInfoLaboratory())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            var analysisFound = this.analysisRepository.findByCodAnalysis(analysisDto.getCodAnalysis());
            if (analysisFound.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            var analysis = analysisFound.get();

            var analysisUpdated = Analysis.builder()
                    .codAnalysis(UUID.randomUUID())
                    .analysisMakerName(analysisDto.getAnalystName())
                    .initialDate(analysisDto.getInitialDate())
                    .finalDate(analysisDto.getFinalDate())
                    .laboratoryId(analysisDto.getInfoLaboratory().getId())
                    .propertyId(analysisDto.getInfoProperty().getId())
                    .notes(analysisDto.getNotes()).build();
            this.analysisRepository.save(analysisUpdated);

            return MessageResponseDto.builder().message("Analise atualizada com sucesso!").build();
        } catch (ResponseStatusException ex) {
            log.error("c=AnalysisService, m=update, m=Failed on updating a analysis, cod={}", analysisDto.getCodAnalysis());
            throw ex;
        }
    }

    @Transactional
    public MessageResponseDto delete(UUID cod) {
        try {
            var analysisFound = this.analysisRepository.findByCodAnalysis(cod);
            if (analysisFound.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            this.analysisRepository.delete(analysisFound.get());
            return MessageResponseDto.builder().message("Analise deletado com sucesso!").build();
        } catch (ResponseStatusException ex) {
            log.error("c=AnalysisService, m=delete, m=Failed on deleting a analysis, cod={}", cod.toString());
            throw ex;
        }
    }


    public boolean validateInitialAndFinalDate(LocalDateTime initialDate, LocalDateTime finalDate) {
        return initialDate.isAfter(finalDate) || finalDate.isBefore(initialDate);
    }

    public boolean validadePropertyAndLaboratoryIds(Long propertyId, Long laboratoryId) {
        var prop = this.propertyRepository.findById(propertyId);
        var lab = this.laboratoryRepository.findById(laboratoryId);
        return prop.isPresent() && lab.isPresent();
    }

    public boolean validadePropertyAndLaborNames(InfoPropertyDto infoPropertyDto, InfoLaboratoryDto infoLaboratoryDto) {
        var prop = this.propertyRepository.findById(infoPropertyDto.getId()).get();
        var lab = this.laboratoryRepository.findById(infoLaboratoryDto.getId()).get();
        return prop.getName().equals(infoPropertyDto.getName()) && lab.getName().equals(infoLaboratoryDto.getName());
    }

}
