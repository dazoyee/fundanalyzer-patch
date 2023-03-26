package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.ValuationEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ValuationDao {

    private static final Logger log = LogManager.getLogger(ValuationDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ValuationDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select valuation by all")
    public List<ValuationEntity> selectAll() {
        return jdbcTemplate.query(
                "SELECT * FROM valuation",
                new BeanPropertyRowMapper<>(ValuationEntity.class)
        );
    }

    @NewSpan("select valuation by unique key")
    public Optional<ValuationEntity> selectByUniqueKey(final String code, final LocalDate submitDate, final LocalDate targetDate) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM valuation WHERE company_code = :companyCode AND submit_date = :submitDate AND target_date = :targetDate",
                    new MapSqlParameterSource(Map.of(
                            "companyCode", code,
                            "submitDate", submitDate,
                            "targetDate", targetDate
                    )),
                    new BeanPropertyRowMapper<>(ValuationEntity.class)
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @NewSpan("update valuation set stock_price_id and investment_indicator_id and valuation_id_of_submit_date and analysis_result_id")
    @Transactional
    public void update(final ValuationEntity entity) {
        StringBuilder sql = new StringBuilder().append("UPDATE valuation SET ");
        Map<String, Integer> map = new java.util.HashMap<>(Map.of("id", entity.getId()));

        entity.getStockPriceId().ifPresent(v -> {
            sql.append("stock_price_id = :stockPriceId, ");
            map.put("stockPriceId", v);
        });
        entity.getInvestmentIndicatorId().ifPresent(v -> {
            sql.append("investment_indicator_id = :investmentIndicatorId, ");
            map.put("investmentIndicatorId", v);
        });
        entity.getValuationIdOfSubmitDate().ifPresent(v -> {
            sql.append("valuation_id_of_submit_date = :valuationIdOfSubmitDate, ");
            map.put("valuationIdOfSubmitDate", v);
        });
        entity.getAnalysisResultId().ifPresent(v -> {
            sql.append("analysis_result_id = :analysisResultId ");
            map.put("analysisResultId", v);
        });

        final int update = jdbcTemplate.update(sql.append("WHERE id = :id").toString(), new MapSqlParameterSource(map));
        if (0 == update) {
            log.warn("not update: {}", entity);
        }
    }
}
