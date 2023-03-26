package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.AnalysisResultDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.InvestmentIndicatorDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.StockPriceDao;
import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.ValuationDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.AnalysisResult;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.InvestmentIndicatorEntity;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.StockPriceEntity;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.ValuationEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateValuationProcessor {

    private static final Logger log = LogManager.getLogger(UpdateValuationProcessor.class);

    private final ValuationDao valuationDao;
    private final StockPriceDao stockPriceDao;
    private final InvestmentIndicatorDao investmentIndicatorDao;
    private final AnalysisResultDao analysisResultDao;

    public UpdateValuationProcessor(
            final ValuationDao valuationDao,
            final StockPriceDao stockPriceDao,
            final InvestmentIndicatorDao investmentIndicatorDao,
            final AnalysisResultDao analysisResultDao) {
        this.valuationDao = valuationDao;
        this.stockPriceDao = stockPriceDao;
        this.investmentIndicatorDao = investmentIndicatorDao;
        this.analysisResultDao = analysisResultDao;
    }

    public void execute() {
        log.info("[START] update valuation");

        valuationDao.selectAll().stream().map(this::modify).forEach(valuationDao::update);

        log.info("[END] update valuation");
    }

    private ValuationEntity modify(final ValuationEntity before) {
        final Optional<StockPriceEntity> stockPriceEntity = stockPriceDao.selectByCodeAndDate(before.getCompanyCode(), before.getTargetDate());
        return new ValuationEntity(
                before.getId(),
                before.getCompanyCode(),
                before.getSubmitDate(),
                before.getTargetDate(),
                stockPriceEntity.map(StockPriceEntity::getId).orElse(null),
                before.getStockPrice(),
                null,
                before.getGoalsStock().orElse(null),
                investmentIndicatorDao.selectByCodeAndDate(before.getCompanyCode(), before.getTargetDate())
                        .map(InvestmentIndicatorEntity::getId).orElse(null),
                before.getGrahamIndex().orElse(null),
                before.getDaySinceSubmitDate(),
                before.getDifferenceFromSubmitDate(),
                before.getSubmitDateRatio(),
                before.getDiscountValue(),
                before.getDiscountRate(),
                before.getCorporateValue(),
                valuationDao.selectByUniqueKey(before.getCompanyCode(), before.getSubmitDate(), before.getSubmitDate())
                        .map(ValuationEntity::getId).orElse(null),
                before.getStockPriceOfSubmitDate(),
                before.getGrahamIndexOfSubmitDate().orElse(null),
                analysisResultDao.selectByDocumentId(before.getDocumentId()).map(AnalysisResult::getId).orElse(null),
                before.getDocumentId(),
                before.getCreatedAt()
        );
    }
}
