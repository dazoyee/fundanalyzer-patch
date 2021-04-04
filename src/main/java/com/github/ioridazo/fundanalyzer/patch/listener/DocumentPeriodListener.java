package com.github.ioridazo.fundanalyzer.patch.listener;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.master.CompanyDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.DocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.DocTypeCode;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.master.Company;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.Document;
import com.github.ioridazo.fundanalyzer.patch.util.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DocumentPeriodListener {

    private static final Logger log = LogManager.getLogger(DocumentPeriodListener.class);

    private final DocumentDao documentDao;
    private final CompanyDao companyDao;

    public DocumentPeriodListener(
            final DocumentDao documentDao,
            final CompanyDao companyDao) {
        this.documentDao = documentDao;
        this.companyDao = companyDao;
    }

    public void execute() {
        final List<Company> companyList = companyDao.selectAll();
        final List<Document> documentList = documentDao.selectByDocumentTypeCode(DocTypeCode.AMENDED_SECURITIES_REPORT).stream()
                .filter(document -> Converter.toCompanyCode(document.getEdinetCode(), companyList).isPresent())
                .collect(Collectors.toList());

        final int target = documentList.size();
        final List<Document> failures = documentList.stream()
                .filter(document -> Objects.isNull(document.getDocumentPeriod()))
                .collect(Collectors.toList());

        if (target == 0) {
            log.info("対象書類はありませんでした。");
        } else if (failures.isEmpty()) {
            final String documentIdListAsString = documentList.stream()
                    .map(Document::getDocumentId)
                    .collect(Collectors.joining(","));
            log.info("すべての対象書類を正常に更新しました。\t{}", documentIdListAsString);
        } else {
            final String documentIdListAsString = failures.stream()
                    .map(Document::getDocumentId)
                    .collect(Collectors.joining(","));
            log.warn("一部の対象書類は更新できませんでした。\t総書類数:{}\t失敗した書類数:{}\n{}",
                    target, failures.size(), documentIdListAsString);
        }
    }
}
