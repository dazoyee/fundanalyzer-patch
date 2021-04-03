package com.github.ioridazo.fundanalyzer.patch.listener;

import org.springframework.stereotype.Component;

@Component
public class PatchListener {

    private final DocumentPeriodListener documentPeriod;
    private final UpdateFinancialStatementListener updateFinancialStatement;
    private final UpdateAnalysisResultListener updateAnalysisResult;

    public PatchListener(
            final DocumentPeriodListener documentPeriod,
            final UpdateFinancialStatementListener updateFinancialStatement,
            final UpdateAnalysisResultListener updateAnalysisResult) {
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
