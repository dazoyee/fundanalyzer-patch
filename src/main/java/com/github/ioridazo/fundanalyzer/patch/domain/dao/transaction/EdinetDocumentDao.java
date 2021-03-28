package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.EdinetDocument;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
