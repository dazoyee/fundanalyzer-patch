package com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValuationEntity {

    private Integer id;

    private String companyCode;

    private LocalDate submitDate;

    private LocalDate targetDate;

    private Integer stockPriceId;

    private BigDecimal stockPrice;

    private BigDecimal dividendYield;

    private BigDecimal goalsStock;

    private Integer investmentIndicatorId;

    private BigDecimal grahamIndex;

    private Long daySinceSubmitDate;

    private BigDecimal differenceFromSubmitDate;

    private BigDecimal submitDateRatio;

    private BigDecimal discountValue;

    private BigDecimal discountRate;

    private BigDecimal corporateValue;

    private Integer valuationIdOfSubmitDate;

    private BigDecimal stockPriceOfSubmitDate;

    private BigDecimal grahamIndexOfSubmitDate;

    private Integer analysisResultId;

    private String documentId;

    private LocalDateTime createdAt;

    public Optional<Integer> getStockPriceId() {
        return Optional.ofNullable(stockPriceId);
    }

    @SuppressWarnings("unused")
    public Optional<BigDecimal> getDividendYield() {
        return Optional.ofNullable(dividendYield);
    }

    public Optional<BigDecimal> getGoalsStock() {
        return Optional.ofNullable(goalsStock);
    }

    public Optional<Integer> getInvestmentIndicatorId() {
        return Optional.ofNullable(investmentIndicatorId);
    }

    public Optional<BigDecimal> getGrahamIndex() {
        return Optional.ofNullable(grahamIndex);
    }

    public Optional<Integer> getValuationIdOfSubmitDate() {
        return Optional.ofNullable(valuationIdOfSubmitDate);
    }

    public Optional<BigDecimal> getGrahamIndexOfSubmitDate() {
        return Optional.ofNullable(grahamIndexOfSubmitDate);
    }

    public Optional<Integer> getAnalysisResultId() {
        return Optional.ofNullable(analysisResultId);
    }
}
