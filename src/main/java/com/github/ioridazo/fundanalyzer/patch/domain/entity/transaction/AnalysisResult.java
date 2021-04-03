package com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AnalysisResult {

    private Integer id;

    private String companyCode;

    private LocalDate documentPeriod;

    private BigDecimal corporateValue;

    private String documentTypeCode;

    private LocalDate submitDate;

    private String documentId;

    private LocalDateTime createdAt;
}
