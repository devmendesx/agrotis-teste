package com.agrotis.labor.service;

import com.agrotis.labor.dto.LaboratoryListViewDto;
import com.agrotis.labor.repository.LaboratoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LaboratoryService {
    private LaboratoryRepository laboratoryRepository;

    public List<LaboratoryListViewDto> findAll() {
        try {
            var laboratories = this.laboratoryRepository.findAll();
            if (laboratories.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return laboratories.stream().map(lab -> LaboratoryListViewDto.builder().id(lab.getId()).name(lab.getName()).build()).collect(Collectors.toList());
        } catch (ResponseStatusException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
