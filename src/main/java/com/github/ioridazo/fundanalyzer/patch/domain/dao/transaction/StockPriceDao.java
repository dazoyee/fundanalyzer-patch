package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.StockPriceEntity;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public class StockPriceDao {

    private static final String NIKKEI_VALUE = "1";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StockPriceDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

        final Predicate<StockPriceEntity> predicate = stockPriceEntity -> Objects.equals(NIKKEI_VALUE, stockPriceEntity.getSourceOf());

        if (list.stream().anyMatch(predicate)) {
            return list.stream().filter(predicate).findFirst();
        } else {
            return list.stream().findAny();
        }
    }
}
