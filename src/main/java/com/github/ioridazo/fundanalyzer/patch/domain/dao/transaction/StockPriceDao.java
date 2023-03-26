package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.StockPriceEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public class StockPriceDao {

    private static final Logger log = LogManager.getLogger(StockPriceDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StockPriceDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select stock_price by stock_price is null")
    public List<StockPriceEntity> selectByStockPriceIsNull() {
        return jdbcTemplate.query(
                "SELECT * FROM `stock_price` WHERE `stock_price` IS NULL",
                new BeanPropertyRowMapper<>(StockPriceEntity.class)
        );
    }

    @NewSpan("select stock_price by company_code and target_date")
    public Optional<StockPriceEntity> selectByCodeAndDate(final String code, final LocalDate targetDate) {
        final List<StockPriceEntity> list = jdbcTemplate.query(
                "SELECT * FROM stock_price WHERE company_code = :companyCode AND target_date = :targetDate",
                new MapSqlParameterSource(Map.of(
                        "companyCode", code,
                        "targetDate", targetDate
                )),
                new BeanPropertyRowMapper<>(StockPriceEntity.class)
        );

        final Predicate<StockPriceEntity> predicate = stockPriceEntity -> stockPriceEntity.getStockPrice().isPresent();

        if (list.stream().anyMatch(predicate)) {
            return list.stream().filter(predicate).findFirst();
        } else {
            return list.stream().findAny();
        }
    }

    @NewSpan("update stock_price set stock_price")
    @Transactional
    public void update(final StockPriceEntity entity) {
        StringBuilder sql = new StringBuilder().append("UPDATE valuation SET ");
        Map<String, String> map = new java.util.HashMap<>(Map.of("id", entity.getId().toString()));

        entity.getStockPrice().ifPresent(v -> {
            sql.append("stock_price = :stockPrice ");
            map.put("stockPrice", v.toString());
        });

        final int update = jdbcTemplate.update(sql.append("WHERE id = :id").toString(), new MapSqlParameterSource(map));
        if (0 == update) {
            log.warn("not update: {}", entity);
        }
    }
}
