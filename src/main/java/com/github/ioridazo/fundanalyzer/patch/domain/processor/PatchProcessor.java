package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import org.springframework.stereotype.Component;

@Component
public class PatchProcessor {

    private final DocumentPeriodProcessor documentPeriod;
    private final UpdateFinancialStatementProcessor updateFinancialStatement;

    public PatchProcessor(
            final DocumentPeriodProcessor documentPeriod,
            final UpdateFinancialStatementProcessor updateFinancialStatement) {
        this.documentPeriod = documentPeriod;
        this.updateFinancialStatement = updateFinancialStatement;
    }

    public void documentPeriod() {
        documentPeriod.documentPeriod();
    }

    public void updateFinancialStatement() {
        updateFinancialStatement.updateFinancialStatement();
    }
}
