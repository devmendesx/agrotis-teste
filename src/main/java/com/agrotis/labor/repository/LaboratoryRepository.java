package com.agrotis.labor.repository;

import com.agrotis.labor.entity.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {

    public Optional<Laboratory> findByCodLaboratory(UUID codLaboratory);

    @Modifying
    @Query("DELETE FROM Laboratory l WHERE l.codLaboratory = :cod")
    public void deleteLaboratoryByCodLaboratory(UUID cod);

}
