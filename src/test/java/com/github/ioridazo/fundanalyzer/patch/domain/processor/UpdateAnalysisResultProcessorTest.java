package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.master.CompanyDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.AnalysisResultDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.DocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.DocTypeCode;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.master.Company;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.AnalysisResult;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UpdateAnalysisResultProcessorTest {

    private  AnalysisResultDao analysisResultDao;
    private  CompanyDao companyDao;
    private  DocumentDao documentDao;

    private UpdateAnalysisResultProcessor processor;

    @BeforeEach
    void setUp() {
        analysisResultDao = Mockito.mock(AnalysisResultDao.class);
        companyDao = Mockito.mock(CompanyDao.class);
        documentDao = Mockito.mock(DocumentDao.class);
        processor = new UpdateAnalysisResultProcessor(analysisResultDao, companyDao, documentDao);
    }

    @Test
    void updateAnalysisResult_ok() {
        var analysisResult1 = new AnalysisResult();
        analysisResult1.setCompanyCode("cc");
        analysisResult1.setDocumentPeriod(LocalDate.of(2019,1,1));
        var analysisResult2 = new AnalysisResult();
        analysisResult2.setCompanyCode("cc");
        analysisResult2.setDocumentPeriod(LocalDate.of(2020,1,1));
        var analysisResult3 = new AnalysisResult();
        analysisResult3.setCompanyCode("cc");
        analysisResult3.setDocumentPeriod(LocalDate.of(2021,1,1));

        final Company company = new Company();
        company.setCode("cc");
        company.setEdinetCode("ec");

        final Document document1 = new Document();
        document1.setDocumentId("d1");
        document1.setDocumentTypeCode("120");
        document1.setSubmitDate(LocalDate.of(2021,4,1));
        final Document document2 = new Document();
        document2.setDocumentId("d2");
        document2.setDocumentTypeCode("120");
        document2.setSubmitDate(LocalDate.of(2021,4,2));
        final Document document3 = new Document();
        document3.setDocumentId("d3");
        document3.setDocumentTypeCode("120");
        document3.setSubmitDate(LocalDate.of(2021,4,3));

        Mockito.when(analysisResultDao.selectAll()).thenReturn(List.of(analysisResult1,analysisResult2, analysisResult3));
        Mockito.when(companyDao.selectAll()).thenReturn(List.of(company));
        Mockito.when(documentDao.selectByDocumentTypeCodeAndEdinetCodeAndDocumentPeriod(
                DocTypeCode.ANNUAL_SECURITIES_REPORT, "ec", LocalDate.of(2019,1,1)))
                .thenReturn(List.of(document1));
        Mockito.when(documentDao.selectByDocumentTypeCodeAndEdinetCodeAndDocumentPeriod(
                DocTypeCode.ANNUAL_SECURITIES_REPORT, "ec", LocalDate.of(2020,1,1)))
                .thenReturn(List.of(document2));
        Mockito.when(documentDao.selectByDocumentTypeCodeAndEdinetCodeAndDocumentPeriod(
                DocTypeCode.ANNUAL_SECURITIES_REPORT, "ec", LocalDate.of(2021,1,1)))
                .thenReturn(List.of(document3));

        assertDoesNotThrow(() -> processor.execute());

        analysisResult1.setDocumentTypeCode("120");
        analysisResult1.setSubmitDate(LocalDate.of(2021,4,1));
        analysisResult1.setDocumentId("d1");
        Mockito.verify(analysisResultDao, Mockito.times(1)).updateAnalysisResult(analysisResult1);
        analysisResult2.setDocumentTypeCode("120");
        analysisResult2.setSubmitDate(LocalDate.of(2021,4,2));
        analysisResult2.setDocumentId("d2");
        Mockito.verify(analysisResultDao, Mockito.times(1)).updateAnalysisResult(analysisResult2);
        analysisResult3.setDocumentTypeCode("120");
        analysisResult3.setSubmitDate(LocalDate.of(2021,4,3));
        analysisResult3.setDocumentId("d3");
        Mockito.verify(analysisResultDao, Mockito.times(1)).updateAnalysisResult(analysisResult3);
    }

    @Test
    void updateAnalysisResult_ng() {
        var analysisResult1 = new AnalysisResult();
        analysisResult1.setCompanyCode("cc");
        analysisResult1.setDocumentPeriod(LocalDate.of(2019,1,1));

        final Company company = new Company();
        company.setCode("cc");
        company.setEdinetCode("ec");

        Mockito.when(analysisResultDao.selectAll()).thenReturn(List.of(analysisResult1));
        Mockito.when(companyDao.selectAll()).thenReturn(List.of(company));

        assertDoesNotThrow(() -> processor.execute());

        Mockito.verify(analysisResultDao, Mockito.times(0)).updateAnalysisResult(any());
    }
}