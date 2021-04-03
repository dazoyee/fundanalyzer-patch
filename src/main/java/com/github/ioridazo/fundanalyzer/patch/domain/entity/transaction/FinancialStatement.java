package com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FinancialStatement {

    private Integer id;

    private String companyCode;

    private String edinetCode;

    private String financialStatementId;

    private String subjectId;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    private Long value;

    private String documentTypeCode;

    private LocalDate submitDate;

    private String documentId;

    private LocalDateTime createdAt;
}
