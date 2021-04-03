package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.DocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.EdinetDocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.DocTypeCode;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class DocumentPeriodProcessor {

    private static final Logger log = LogManager.getLogger(DocumentPeriodProcessor.class);

    private final DocumentDao documentDao;
    private final EdinetDocumentDao edinetDocumentDao;

    public DocumentPeriodProcessor(
            final DocumentDao documentDao,
            final EdinetDocumentDao edinetDocumentDao) {
        this.documentDao = documentDao;
        this.edinetDocumentDao = edinetDocumentDao;
    }

    public void execute() {
        log.info("[START] update document period");
        // 更新するdocument一覧
        final List<Document> documentList = documentDao.selectByDocumentTypeCode(DocTypeCode.AMENDED_SECURITIES_REPORT);
        log.info("更新する対象の書類数は{}件です。", documentList.size());

        documentList.forEach(document -> {
            try {
                // 親書類を見つける
                final Document parentDocument = Optional.of(document)
                        .map(d -> edinetDocumentDao.selectByDocId(d.getDocumentId()))
                        .map(edinetDocument -> documentDao.selectByDocumentId(edinetDocument.getParentDocId().orElseThrow()))
                        .orElseThrow();

                // document_periodを更新
                document.setDocumentPeriod(parentDocument.getDocumentPeriod());
                documentDao.updateDocumentPeriod(document);
                log.info("{}", document);
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
