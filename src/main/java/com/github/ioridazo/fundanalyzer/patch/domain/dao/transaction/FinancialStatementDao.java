package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.FinancialStatement;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public class FinancialStatementDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FinancialStatementDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select financial_statement by all")
    public List<FinancialStatement> selectAll() {
        return jdbcTemplate.query(
                "SELECT * FROM financial_statement",
                new BeanPropertyRowMapper<>(FinancialStatement.class)
        );
    }

    @NewSpan("update financial_statement set document_type_code and document_id and submit_date")
    @Transactional
    public void updateFinancialStatement(final FinancialStatement financialStatement) {
        jdbcTemplate.update(
                "UPDATE financial_statement SET " +
                        "document_type_code = :documentTypeCode, " +
                        "document_id = :documentId, " +
                        "submit_date = :submitDate " +
                        "WHERE id = :id",
                new MapSqlParameterSource(Map.of(
                        "documentTypeCode", financialStatement.getDocumentTypeCode(),
                        "documentId", financialStatement.getDocumentId(),
                        "submitDate", financialStatement.getSubmitDate(),
                        "id", financialStatement.getId()
                ))
        );
    }
}
