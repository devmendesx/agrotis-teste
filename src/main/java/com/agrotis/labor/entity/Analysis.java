package com.agrotis.labor.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "analysis")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Type(type = "uuid-char")
    private UUID codAnalysis;

    @Column
    private String analysisMakerName;

    @Column
    private LocalDateTime initialDate;

    @Column
    private LocalDateTime finalDate;

    @Column
    private Long propertyId;

    @Column
    private Long laboratoryId;

    @Column
    private String notes;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

}
