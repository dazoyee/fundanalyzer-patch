package com.github.ioridazo.fundanalyzer.patch.domain.processor;

import com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction.StockPriceDao;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.StockPriceEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UpdateStockPriceProcessor {

    private static final Logger log = LogManager.getLogger(UpdateStockPriceProcessor.class);

    private final StockPriceDao stockPriceDao;

    public UpdateStockPriceProcessor(
            final StockPriceDao stockPriceDao) {
        this.stockPriceDao = stockPriceDao;
    }

    public void execute() {
        log.info("[START] update stock_price");

        final List<StockPriceEntity> entityList = stockPriceDao.selectByStockPriceIsNull();
        log.info("target size is {}", entityList.size());

        final List<StockPriceEntity> executeList = entityList.stream()
                .map(this::modify)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        log.info("execute list size is {}", executeList.size());

        executeList.forEach(stockPriceDao::update);

        log.info("[END] update stock_price");
    }

    private Optional<StockPriceEntity> modify(final StockPriceEntity before) {
        final Optional<StockPriceEntity> after = stockPriceDao.selectByCodeAndDate(before.getCompanyCode(), before.getTargetDate());
        final Optional<Double> stockPrice = after.flatMap(StockPriceEntity::getStockPrice);

        if (stockPrice.isPresent()) {
            final StockPriceEntity entity = new StockPriceEntity();
            entity.setId(before.getId());
            entity.setStockPrice(stockPrice.get());
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }
}
