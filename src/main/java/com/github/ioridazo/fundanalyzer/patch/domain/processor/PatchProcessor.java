package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import org.springframework.stereotype.Component;

@Component
public class PatchProcessor {

    private final DocumentPeriodProcessor documentPeriod;
    private final UpdateFinancialStatementProcessor updateFinancialStatement;
    private final UpdateAnalysisResultProcessor updateAnalysisResult;
    private final UpdateValuationProcessor updateValuation;
    private final UpdateStockPriceProcessor updateStockPrice;

    public PatchProcessor(
            final DocumentPeriodProcessor documentPeriod,
            final UpdateFinancialStatementProcessor updateFinancialStatement,
            final UpdateAnalysisResultProcessor updateAnalysisResult,
            final UpdateValuationProcessor updateValuation,
            final UpdateStockPriceProcessor updateStockPrice) {
        this.documentPeriod = documentPeriod;
        this.updateFinancialStatement = updateFinancialStatement;
        this.updateAnalysisResult = updateAnalysisResult;
        this.updateValuation = updateValuation;
        this.updateStockPrice = updateStockPrice;
    }

    public void documentPeriod() {
        documentPeriod.execute();
    }

    public void updateFinancialStatement() {
        updateFinancialStatement.execute();
    }

    public void updateAnalysisResult() {
        updateAnalysisResult.execute();
    }

    public void updateValuation() {
        updateValuation.execute();
    }

    public void updateStockPrice() {
        updateStockPrice.execute();
    }
}
