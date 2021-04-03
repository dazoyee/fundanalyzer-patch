package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import org.springframework.stereotype.Component;

@Component
public class PatchProcessor {

    private final DocumentPeriodProcessor documentPeriod;
    private final UpdateFinancialStatementProcessor updateFinancialStatement;
    private final UpdateAnalysisResultProcessor updateAnalysisResult;

    public PatchProcessor(
            final DocumentPeriodProcessor documentPeriod,
            final UpdateFinancialStatementProcessor updateFinancialStatement,
            final UpdateAnalysisResultProcessor updateAnalysisResult) {
        this.documentPeriod = documentPeriod;
        this.updateFinancialStatement = updateFinancialStatement;
        this.updateAnalysisResult = updateAnalysisResult;
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
}
