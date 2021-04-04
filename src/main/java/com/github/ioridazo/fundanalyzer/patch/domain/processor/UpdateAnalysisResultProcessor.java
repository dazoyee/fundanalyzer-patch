package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.master.CompanyDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.AnalysisResultDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.DocumentDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.DocTypeCode;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.master.Company;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.AnalysisResult;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.Document;
import com.github.ioridazo.fundanalyzer.patch.util.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UpdateAnalysisResultProcessor {

    private static final Logger log = LogManager.getLogger(UpdateAnalysisResultProcessor.class);

    private final AnalysisResultDao analysisResultDao;
    private final CompanyDao companyDao;
    private final DocumentDao documentDao;

    public UpdateAnalysisResultProcessor(
            final AnalysisResultDao analysisResultDao,
            final CompanyDao companyDao,
            final DocumentDao documentDao) {
        this.analysisResultDao = analysisResultDao;
        this.companyDao = companyDao;
        this.documentDao = documentDao;
    }

    public void execute() {
        log.info("[START] update analysis result");
        // 更新するanalysis_result一覧
        final List<AnalysisResult> analysisResultList = analysisResultDao.selectAll().stream()
                .filter(analysisResult -> Objects.isNull(analysisResult.getDocumentTypeCode())
                        || Objects.isNull(analysisResult.getDocumentId())
                        || Objects.isNull(analysisResult.getSubmitDate()))
                .collect(Collectors.toList());
        log.info("更新する対象のカラムは{}件です。", analysisResultList.size());

        final List<Company> companyList = companyDao.selectAll();
        analysisResultList.parallelStream().forEach(analysisResult -> {
            final Optional<String> edinetCode = Converter.toEdinetCode(analysisResult.getCompanyCode(), companyList);

            if (edinetCode.isPresent()) {
                final List<Document> documentList = documentDao.selectByDocumentTypeCodeAndEdinetCodeAndDocumentPeriod(
                        DocTypeCode.ANNUAL_SECURITIES_REPORT,
                        edinetCode.get(),
                        analysisResult.getDocumentPeriod()
                ).stream()
                        .filter(document -> Objects.nonNull(document.getNumberOfSharesDocumentPath()))
                        .collect(Collectors.toList());

                if (documentList.size() == 1) {
                    try {
                        final String documentTypeCode = Objects.requireNonNull(documentList.get(0).getDocumentTypeCode());
                        final LocalDate submitDate = Objects.requireNonNull(documentList.get(0).getSubmitDate());
                        final String documentId = Objects.requireNonNull(documentList.get(0).getDocumentId());

                        // analysis_resultを更新
                        analysisResult.setDocumentTypeCode(documentTypeCode);
                        analysisResult.setSubmitDate(submitDate);
                        analysisResult.setDocumentId(documentId);

                        analysisResultDao.updateAnalysisResult(analysisResult);
                        log.info("{}", analysisResult);
                    } catch (NullPointerException e) {
                        log.warn("必須項目に値がありません。" +
                                        "\tanalysis_result_id:{}\tdoc_type_code:{}\tsubmit_date:{}\tdocument_id:{}",
                                analysisResult.getId(),
                                documentList.get(0).getDocumentTypeCode(),
                                documentList.get(0).getSubmitDate(),
                                documentList.get(0).getDocumentId());
                    }
                } else if (documentList.isEmpty()) {
                    log.warn("書類が見つかりませんでした。" +
                                    "\tdocument_type_code:{}\tcompany_code:{}\tedinet_code:{}\tdocument_id:{}",
                            DocTypeCode.ANNUAL_SECURITIES_REPORT.toValue(),
                            analysisResult.getCompanyCode(),
                            edinetCode,
                            analysisResult.getDocumentId());

                } else {
                    log.warn("関連書類が複数見つかりました。\tdocument_size:{}" +
                                    "\tdocument_type_code:{}\tcompany_code:{}\tedinet_code:{}\tdocument_id:{}",
                            documentList.size(),
                            DocTypeCode.ANNUAL_SECURITIES_REPORT.toValue(),
                            analysisResult.getCompanyCode(),
                            edinetCode,
                            analysisResult.getDocumentId());
                }
            } else {
                log.warn("edinet_codeが存在しませんでした。\tcompany_code:{}\tdocument_period:{}",
                        analysisResult.getCompanyCode(), analysisResult.getDocumentPeriod());
            }
        });
        log.info("[END] update analysis result");
    }
}
