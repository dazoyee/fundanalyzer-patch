package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.InvestmentIndicatorEntity;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Repository
public class InvestmentIndicatorDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InvestmentIndicatorDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select investment_indicator by company_code and target_date")
    public Optional<InvestmentIndicatorEntity> selectByCodeAndDate(final String code, final LocalDate targetDate) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM investment_indicator WHERE company_code = :companyCode AND target_date = :targetDate",
                    new MapSqlParameterSource(Map.of(
                            "companyCode", code,
                            "targetDate", targetDate
                    )),
                    new BeanPropertyRowMapper<>(InvestmentIndicatorEntity.class)
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
