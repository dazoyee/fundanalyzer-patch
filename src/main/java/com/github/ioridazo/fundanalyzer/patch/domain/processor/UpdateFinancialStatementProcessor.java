package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.EdinetDocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.FinancialStatementDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.EdinetDocument;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.FinancialStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UpdateFinancialStatementProcessor {

    private static final Logger log = LogManager.getLogger(UpdateFinancialStatementProcessor.class);

    private final FinancialStatementDao financialStatementDao;
    private final EdinetDocumentDao edinetDocumentDao;

    public UpdateFinancialStatementProcessor(
            final FinancialStatementDao financialStatementDao,
            final EdinetDocumentDao edinetDocumentDao) {
        this.financialStatementDao = financialStatementDao;
        this.edinetDocumentDao = edinetDocumentDao;
    }

    public void execute() {
        log.info("[START] update financial statement");
        // 更新するfinancial_statement一覧
        final List<FinancialStatement> financialStatementList = financialStatementDao.selectAll();
        log.info("更新する対象のカラムは{}件です。", financialStatementList.size());

        financialStatementList.forEach(financialStatement -> {
            final Optional<EdinetDocument> edinetDocument = edinetDocumentDao.selectByEdinetCodeAndPeriodStartAndPeriodEnd(
                    financialStatement.getEdinetCode(),
                    financialStatement.getPeriodStart(),
                    financialStatement.getPeriodEnd()
            );

            if (edinetDocument.isPresent()){
                try {
                    final String docTypeCode = Objects.requireNonNull(edinetDocument.get().getDocTypeCode());
                    final String docId = Objects.requireNonNull(edinetDocument.get().getDocId());
                    final String submitDateTime = Objects.requireNonNull(edinetDocument.get().getSubmitDateTime());

                    // financial_statementを更新
                    financialStatement.setDocumentTypeCode(docTypeCode);
                    financialStatement.setDocumentId(docId);
                    financialStatement.setSubmitDate(LocalDateTime.parse(submitDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toLocalDate());

                    financialStatementDao.updateFinancialStatement(financialStatement);
                    log.info("{}", financialStatement);
                } catch (NullPointerException e) {
                    log.warn("必須項目に値がありません。" +
                                    "\tfinancial_statement_id:{}\tdoc_type_code:{}\tdoc_id:{}\tsubmit_date:{}",
                            financialStatement.getId(),
                            edinetDocument.get().getDocTypeCode(),
                            edinetDocument.get().getDocId(),
                            edinetDocument.get().getSubmitDateTime());
                }
            }else {
                log.warn("edinet_documentが存在しませんでした。\tedinet_code:{}\tperiod_start:{}\tperiod_end:{}",
                        financialStatement.getEdinetCode(),
                        financialStatement.getPeriodStart(),
                        financialStatement.getPeriodEnd());
            }
        });
        log.info("[END] update financial statement");
    }
}
