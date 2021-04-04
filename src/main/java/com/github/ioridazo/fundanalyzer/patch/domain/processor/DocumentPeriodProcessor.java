package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.master.CompanyDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.DocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.EdinetDocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.DocTypeCode;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.master.Company;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.Document;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.EdinetDocument;
import com.github.ioridazo.fundanalyzer.patch.util.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DocumentPeriodProcessor {

    private static final Logger log = LogManager.getLogger(DocumentPeriodProcessor.class);

    private final DocumentDao documentDao;
    private final EdinetDocumentDao edinetDocumentDao;
    private final CompanyDao companyDao;

    public DocumentPeriodProcessor(
            final DocumentDao documentDao,
            final EdinetDocumentDao edinetDocumentDao,
            final CompanyDao companyDao) {
        this.documentDao = documentDao;
        this.edinetDocumentDao = edinetDocumentDao;
        this.companyDao = companyDao;
    }

    public void execute() {
        log.info("[START] update document period");
        final List<Company> companyList = companyDao.selectAll();
        // 更新するdocument一覧
        final List<Document> documentList = documentDao.selectByDocumentTypeCode(DocTypeCode.AMENDED_SECURITIES_REPORT).stream()
                .filter(document -> Converter.toCompanyCode(document.getEdinetCode(), companyList).isPresent())
                .collect(Collectors.toList());
        log.info("更新する対象の書類数は{}件です。", documentList.size());

        documentList.forEach(document -> {
            try {
                // 親書類を見つける
                final EdinetDocument edinetDocument = edinetDocumentDao.selectByDocId(document.getDocumentId());

                if (edinetDocument.getParentDocId().isPresent()) {
                    final Optional<Document> parentDocument = documentDao.selectByDocumentId(edinetDocument.getParentDocId().get());

                    if (parentDocument.isPresent()) {
                        // document_periodを更新
                        document.setDocumentPeriod(parentDocument.get().getDocumentPeriod());
                        documentDao.updateDocumentPeriod(document);
                        log.info("{}", document);
                    } else {
                        log.warn("親書類情報がdocumentテーブルに存在しませんでした。\tdocument_id:{}\tparent_doc_id:{}",
                                document.getDocumentId(), edinetDocument.getParentDocId().get());
                    }
                } else {
                    log.warn("親書類が存在しませんでした。\tdocument_id:{}", document.getDocumentId());
                }
            } catch (NoSuchElementException e) {
                log.warn(
                        "親書類を取得できませんでした。\t対象書類ID:{}\t書類種別コード:{}",
                        document.getDocumentId(),
                        document.getDocumentTypeCode()
                );
            }
        });
        log.info("[END] update document period");
    }
}
