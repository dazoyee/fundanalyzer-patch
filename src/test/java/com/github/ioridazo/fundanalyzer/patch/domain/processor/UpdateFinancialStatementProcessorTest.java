package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.EdinetDocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.FinancialStatementDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.EdinetDocument;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.FinancialStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class UpdateFinancialStatementProcessorTest {

    private FinancialStatementDao financialStatementDao;
    private EdinetDocumentDao edinetDocumentDao;

    private UpdateFinancialStatementProcessor processor;

    @BeforeEach
    void setUp() {
        financialStatementDao = Mockito.mock(FinancialStatementDao.class);
        edinetDocumentDao = Mockito.mock(EdinetDocumentDao.class);
        processor = new UpdateFinancialStatementProcessor(financialStatementDao, edinetDocumentDao);
    }

    @Test
    void updateFinancialStatement_ok() {
        var financialStatement1 = new FinancialStatement();
        financialStatement1.setEdinetCode("e1");
        financialStatement1.setPeriodStart(LocalDate.of(2021, 1,1));
        financialStatement1.setPeriodEnd(LocalDate.of(2021,12,31));
        var financialStatement2 = new FinancialStatement();
        financialStatement2.setEdinetCode("e2");
        financialStatement2.setPeriodStart(LocalDate.of(2021, 1,1));
        financialStatement2.setPeriodEnd(LocalDate.of(2021,12,31));
        var financialStatement3 = new FinancialStatement();
        financialStatement3.setEdinetCode("e2");
        financialStatement3.setPeriodStart(LocalDate.of(2021, 1,1));
        financialStatement3.setPeriodEnd(LocalDate.of(2021,12,31));
        var financialStatement4 = new FinancialStatement();
        financialStatement4.setEdinetCode("e4");
        financialStatement4.setPeriodStart(LocalDate.of(2021, 1,1));
        financialStatement4.setPeriodEnd(LocalDate.of(2021,12,31));

        final EdinetDocument edinetDocument1 = new EdinetDocument();
        edinetDocument1.setDocTypeCode("120");
        edinetDocument1.setDocId("d1");
        edinetDocument1.setSubmitDateTime("2020-05-22 09:09");
        final EdinetDocument edinetDocument2 = new EdinetDocument();
        edinetDocument2.setDocTypeCode("120");
        edinetDocument2.setDocId("d2");
        edinetDocument2.setSubmitDateTime("2021-05-22 09:09");
        final EdinetDocument edinetDocument4 = new EdinetDocument();
        edinetDocument4.setDocTypeCode("120");
        edinetDocument4.setDocId("d4");
        edinetDocument4.setSubmitDateTime("2021-06-22 09:09");

        Mockito.when(financialStatementDao.selectAll())
                .thenReturn(List.of(financialStatement1, financialStatement2,financialStatement3,financialStatement4));
        Mockito.when(edinetDocumentDao.selectByEdinetCodeAndPeriodStartAndPeriodEnd(
                "e1", LocalDate.of(2021, 1,1), LocalDate.of(2021,12,31)))
                .thenReturn(edinetDocument1);
        Mockito.when(edinetDocumentDao.selectByEdinetCodeAndPeriodStartAndPeriodEnd(
                "e2", LocalDate.of(2021, 1,1), LocalDate.of(2021,12,31)))
                .thenReturn(edinetDocument2);
        Mockito.when(edinetDocumentDao.selectByEdinetCodeAndPeriodStartAndPeriodEnd(
                "e4", LocalDate.of(2021, 1,1), LocalDate.of(2021,12,31)))
                .thenReturn(edinetDocument4);

        assertDoesNotThrow(() -> processor.execute());

        financialStatement1.setDocumentTypeCode("120");
        financialStatement1.setDocumentId("d1");
        financialStatement1.setSubmitDate(LocalDate.parse("2020-05-22"));
        Mockito.verify(financialStatementDao, Mockito.times(1)).updateFinancialStatement(financialStatement1);
        financialStatement2.setDocumentTypeCode("120");
        financialStatement2.setDocumentId("d2");
        financialStatement2.setSubmitDate(LocalDate.parse("2021-05-22"));
        Mockito.verify(financialStatementDao, Mockito.times(2)).updateFinancialStatement(financialStatement2);
        financialStatement4.setDocumentTypeCode("120");
        financialStatement4.setDocumentId("d4");
        financialStatement4.setSubmitDate(LocalDate.parse("2021-06-22"));
        Mockito.verify(financialStatementDao, Mockito.times(1)).updateFinancialStatement(financialStatement4);
    }

    @Test
    void updateFinancialStatement_ng() {
        var financialStatement1 = new FinancialStatement();
        financialStatement1.setEdinetCode("e1");
        financialStatement1.setPeriodStart(LocalDate.of(2021, 1,1));
        financialStatement1.setPeriodEnd(LocalDate.of(2021,12,31));

        final EdinetDocument edinetDocument1 = new EdinetDocument();
        edinetDocument1.setDocTypeCode("120");
        edinetDocument1.setDocId("d1");

        Mockito.when(financialStatementDao.selectAll()).thenReturn(List.of(financialStatement1));
        Mockito.when(edinetDocumentDao.selectByEdinetCodeAndPeriodStartAndPeriodEnd(
                "e1", LocalDate.of(2021, 1,1), LocalDate.of(2021,12,31)))
                .thenReturn(edinetDocument1);

        assertDoesNotThrow(() -> processor.execute());

        financialStatement1.setDocumentTypeCode("120");
        financialStatement1.setDocumentId("d1");
        financialStatement1.setSubmitDate(LocalDate.parse("2020-05-22"));
        Mockito.verify(financialStatementDao, Mockito.times(0)).updateFinancialStatement(any());
    }
}