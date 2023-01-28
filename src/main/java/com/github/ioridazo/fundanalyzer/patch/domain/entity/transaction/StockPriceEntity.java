package com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class StockPriceEntity {

    private Integer id;

    private String companyCode;

    private LocalDate targetDate;

    private Double stockPrice;

    private Double openingPrice;

    private Double highPrice;

    private Double lowPrice;

    private Integer volume;

    private String per;

    private String pbr;

    private String roe;

    private String numberOfShares;

    private String marketCapitalization;

    private String dividendYield;

    private String shareholderBenefit;

    private String sourceOf;

    private LocalDateTime createdAt;

    @SuppressWarnings("unused")
    public Optional<Double> getStockPrice() {
        return Optional.ofNullable(stockPrice);
    }

    @SuppressWarnings("unused")
    public Optional<Double> getOpeningPrice() {
        return Optional.ofNullable(openingPrice);
    }

    @SuppressWarnings("unused")
    public Optional<Double> getHighPrice() {
        return Optional.ofNullable(highPrice);
    }

    @SuppressWarnings("unused")
    public Optional<Double> getLowPrice() {
        return Optional.ofNullable(lowPrice);
    }

    @SuppressWarnings("unused")
    public Optional<Integer> getVolume() {
        return Optional.ofNullable(volume);
    }

    @SuppressWarnings("unused")
    public Optional<String> getPer() {
        return Optional.ofNullable(per);
    }

    @SuppressWarnings("unused")
    public Optional<String> getPbr() {
        return Optional.ofNullable(pbr);
    }

    @SuppressWarnings("unused")
    public Optional<String> getRoe() {
        return Optional.ofNullable(roe);
    }

    @SuppressWarnings("unused")
    public Optional<String> getNumberOfShares() {
        return Optional.ofNullable(numberOfShares);
    }

    @SuppressWarnings("unused")
    public Optional<String> getMarketCapitalization() {
        return Optional.ofNullable(marketCapitalization);
    }

    @SuppressWarnings("unused")
    public Optional<String> getDividendYield() {
        return Optional.ofNullable(dividendYield);
    }

    @SuppressWarnings("unused")
    public Optional<String> getShareholderBenefit() {
        return Optional.ofNullable(shareholderBenefit);
    }
}
