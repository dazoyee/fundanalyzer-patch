package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.master.CompanyDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.DocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.EdinetDocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.DocTypeCode;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.master.Company;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.Document;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.EdinetDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;

class DocumentPeriodProcessorTest {

    private DocumentDao documentDao;
    private EdinetDocumentDao edinetDocumentDao;
    private CompanyDao companyDao;

    private DocumentPeriodProcessor processor;

    @BeforeEach
    void setUp() {
        documentDao = Mockito.mock(DocumentDao.class);
        edinetDocumentDao = Mockito.mock(EdinetDocumentDao.class);
        companyDao = Mockito.mock(CompanyDao.class);

        processor = new DocumentPeriodProcessor(documentDao, edinetDocumentDao, companyDao);
    }

    @Test
    void documentPeriod_ok() {
        var company = new Company();
        company.setCode("c");
        company.setEdinetCode("ec");

        var document = new Document();
        document.setDocumentId("documentId");
        document.setEdinetCode("ec");
        document.setDocumentTypeCode("130");

        var edinetDocument = new EdinetDocument();
        edinetDocument.setDocId("documentId");
        edinetDocument.setParentDocId("parentDocId");

        var parentDocument = new Document();
        parentDocument.setDocumentId("parentDocId");
        parentDocument.setDocumentPeriod(LocalDate.of(2021, 3, 28));

        Mockito.when(companyDao.selectAll()).thenReturn(List.of(company));
        Mockito.when(documentDao.selectByDocumentTypeCode(DocTypeCode.AMENDED_SECURITIES_REPORT)).thenReturn(List.of(document));
        Mockito.when(edinetDocumentDao.selectByDocId("documentId")).thenReturn(edinetDocument);
        Mockito.when(documentDao.selectByDocumentId("parentDocId")).thenReturn(Optional.of(parentDocument));

        assertDoesNotThrow(() -> processor.execute());

        var updateDocument = new Document();
        updateDocument.setDocumentId("documentId");
        updateDocument.setEdinetCode("ec");
        updateDocument.setDocumentTypeCode("130");
        updateDocument.setDocumentPeriod(LocalDate.of(2021, 3, 28));

        Mockito.verify(documentDao, Mockito.times(1)).updateDocumentPeriod(updateDocument);
    }

    @Test
    void documentPeriod_ng() {
        var document = new Document();
        document.setDocumentId("documentId");
        document.setDocumentTypeCode("130");

        var edinetDocument = new EdinetDocument();
        edinetDocument.setDocId("documentId");
        edinetDocument.setParentDocId("parentDocId");

        Mockito.when(documentDao.selectByDocumentTypeCode(DocTypeCode.AMENDED_SECURITIES_REPORT)).thenReturn(List.of(document));
        Mockito.when(edinetDocumentDao.selectByDocId("documentId")).thenReturn(edinetDocument);

        assertDoesNotThrow(() -> processor.execute());

        Mockito.verify(documentDao, Mockito.times(0)).updateDocumentPeriod(any());
    }
}