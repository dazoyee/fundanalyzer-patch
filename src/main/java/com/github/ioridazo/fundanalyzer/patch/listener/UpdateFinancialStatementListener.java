package com.github.ioridazo.fundanalyzer.patch.listener;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.FinancialStatementDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.FinancialStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UpdateFinancialStatementListener {

    private static final Logger log = LogManager.getLogger(UpdateFinancialStatementListener.class);

    private final FinancialStatementDao financialStatementDao;

    public UpdateFinancialStatementListener(final FinancialStatementDao financialStatementDao) {
        this.financialStatementDao = financialStatementDao;
    }

    public void execute() {
        final List<FinancialStatement> financialStatementList = financialStatementDao.selectAll();

        final int countAll = financialStatementList.size();
        final long countDocumentTypeCode = financialStatementList.stream().map(FinancialStatement::getDocumentTypeCode).count();
        final long countDocumentId = financialStatementList.stream().map(FinancialStatement::getDocumentId).count();
        final long countSubmitDate = financialStatementList.stream().map(FinancialStatement::getSubmitDate).count();

        if (countAll == 0) {
            log.info("financial_statementにデータがありませんでした。");
        } else if (countAll == countDocumentTypeCode && countAll == countDocumentId && countAll == countSubmitDate) {
            log.info("すべてのfinancial_statementを正常に更新しました。\tcount:{}", countAll);
        } else {
            log.warn("一部のfinancial_statementは更新できませんでした。\tcount:{}", countAll);
            if (countAll != countDocumentTypeCode) {
                log.info("document_type_code\t更新数:{}\t未更新数:{}", countDocumentTypeCode, countAll-countDocumentTypeCode);
                final String financialStatementIds = financialStatementList.stream()
                        .filter(financialStatement -> financialStatement.getDocumentTypeCode().isEmpty())
                        .map(FinancialStatement::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                log.info("確認id:{}", financialStatementIds);
            }
            if (countAll != countDocumentId) {
                log.info("document_id\t更新数:{}\t未更新数:{}", countDocumentId, countAll-countDocumentId);
                final String financialStatementIds = financialStatementList.stream()
                        .filter(financialStatement -> financialStatement.getDocumentId().isEmpty())
                        .map(FinancialStatement::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                log.info("確認id:{}", financialStatementIds);
            }
            if (countAll != countSubmitDate) {
                log.info("submit_date\t更新数:{}\t未更新数:{}", countSubmitDate, countAll-countSubmitDate);
                final String financialStatementIds = financialStatementList.stream()
                        .filter(financialStatement -> Objects.isNull(financialStatement.getSubmitDate()))
                        .map(FinancialStatement::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                log.info("確認id:{}", financialStatementIds);
            }
        }
    }
}
