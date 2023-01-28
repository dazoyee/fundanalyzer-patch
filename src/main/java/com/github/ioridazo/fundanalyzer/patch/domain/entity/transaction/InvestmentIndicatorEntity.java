package com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class InvestmentIndicatorEntity {

    private Integer id;

    private Integer stockId;

    private Integer analysisResultId;

    private String companyCode;

    private LocalDate targetDate;

    private BigDecimal priceCorporateValueRatio;

    private BigDecimal per;

    private BigDecimal pbr;

    private BigDecimal grahamIndex;

    private String documentId;

    private LocalDateTime createdAt;

    @SuppressWarnings("unused")
    public Optional<BigDecimal> getPer() {
        return Optional.ofNullable(per);
    }

    @SuppressWarnings("unused")
    public Optional<BigDecimal> getPbr() {
        return Optional.ofNullable(pbr);
    }

    @SuppressWarnings("unused")
    public Optional<BigDecimal> getGrahamIndex() {
        return Optional.ofNullable(grahamIndex);
    }
}
