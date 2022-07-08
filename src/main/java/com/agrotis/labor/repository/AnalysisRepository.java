package com.agrotis.labor.repository;

import com.agrotis.labor.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    public Optional<Analysis> findByCodAnalysis(UUID codLaboratory);
}
