package com.github.ioridazo.fundanalyzer.patch.listener;

import org.springframework.stereotype.Component;

@Component
public class PatchListener {

    private final DocumentPeriodListener documentPeriod;
    private final UpdateFinancialStatementListener updateFinancialStatement;

    public PatchListener(
            final DocumentPeriodListener documentPeriod,
            final UpdateFinancialStatementListener updateFinancialStatement) {
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
