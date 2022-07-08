package com.agrotis.labor.repository;

import com.agrotis.labor.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    public Optional<Property> findByCodProperty(UUID codLaboratory);

    @Modifying
    @Query("DELETE FROM Property l WHERE l.codProperty = :cod")
    public void deletePropertyByCod(UUID cod);

    @Query("SELECT p FROM Property p WHERE p.cnpj = :cnpj")
    public Optional<Property> findByCnpj(String cnpj);

}
