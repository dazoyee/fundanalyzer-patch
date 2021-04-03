package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.AnalysisResult;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public class AnalysisResultDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AnalysisResultDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select analysis_result by all")
    public List<AnalysisResult> selectAll() {
        return jdbcTemplate.query(
                "SELECT * FROM analysis_result",
                new BeanPropertyRowMapper<>(AnalysisResult.class)
        );
    }

    @NewSpan("update analysis_result set document_type_code and submit_date and document_id")
    @Transactional
    public void updateAnalysisResult(final AnalysisResult analysisResult) {
        jdbcTemplate.update(
                "UPDATE analysis_result SET " +
                        "document_type_code = :documentTypeCode, " +
                        "submit_date = :submitDate, " +
                        "document_id = :documentId " +
                        "WHERE id = :id",
                new MapSqlParameterSource(Map.of(
                        "documentTypeCode", analysisResult.getDocumentTypeCode(),
                        "documentId", analysisResult.getDocumentId(),
                        "submitDate", analysisResult.getSubmitDate(),
                        "id", analysisResult.getId()
                ))
        );
    }
}
