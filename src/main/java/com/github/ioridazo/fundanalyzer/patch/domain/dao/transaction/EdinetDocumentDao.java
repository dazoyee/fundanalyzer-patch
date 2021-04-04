package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.EdinetDocument;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class EdinetDocumentDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EdinetDocumentDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select edinet_document by doc_id")
    public EdinetDocument selectByDocId(final String docId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM edinet_document WHERE doc_id = :docId",
                new MapSqlParameterSource("docId", docId),
                new BeanPropertyRowMapper<>(EdinetDocument.class)
        );
    }

    @NewSpan("select edinet_document by edinet_code and period_start and period_end")
    public List<EdinetDocument> selectByEdinetCodeAndPeriodStartAndPeriodEnd(
            final String edinetCode, final LocalDate periodStart, final LocalDate periodEnd) {
        return jdbcTemplate.query(
                "SELECT * FROM edinet_document " +
                        "WHERE edinet_code = :edinetCode " +
                        "AND period_start = :periodStart " +
                        "AND period_end = :periodEnd",
                new MapSqlParameterSource(Map.of(
                        "edinetCode", edinetCode,
                        "periodStart", periodStart,
                        "periodEnd", periodEnd
                )),
                new BeanPropertyRowMapper<>(EdinetDocument.class)
        );
    }
}
