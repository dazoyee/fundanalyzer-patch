package com.github.ioridazo.fundanalyzer.patch.listener;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.AnalysisResultDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.AnalysisResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateAnalysisResultListener {

    private static final Logger log = LogManager.getLogger(UpdateAnalysisResultListener.class);

    private final AnalysisResultDao analysisResultDao;

    public UpdateAnalysisResultListener(final AnalysisResultDao analysisResultDao) {
        this.analysisResultDao = analysisResultDao;
    }

    public void execute() {
        final List<AnalysisResult> analysisResultList = analysisResultDao.selectAll();

        final int countAll = analysisResultList.size();
        final long countDocumentTypeCode = analysisResultList.stream().map(AnalysisResult::getDocumentTypeCode).count();
        final long countSubmitDate = analysisResultList.stream().map(AnalysisResult::getSubmitDate).count();
        final long countDocumentId = analysisResultList.stream().map(AnalysisResult::getDocumentId).count();

        if (countAll == 0) {
            log.info("analysis_resultにデータがありませんでした。");
        } else if (countAll == countDocumentTypeCode && countAll == countDocumentId && countAll == countSubmitDate) {
            log.info("すべてのanalysis_resultを正常に更新しました。\tcount:{}", countAll);
        } else {
            log.warn("一部のanalysis_resultは更新できませんでした。\tcount:{}", countAll);
            if (countAll != countDocumentTypeCode) {
                log.info("document_type_code更新数:{}", countDocumentTypeCode);
            }
            if (countAll != countDocumentId) {
                log.info("document_id更新数:{}", countDocumentId);
            }
            if (countAll != countSubmitDate) {
                log.info("submit_date更新数:{}", countSubmitDate);
            }
        }


    }
}
