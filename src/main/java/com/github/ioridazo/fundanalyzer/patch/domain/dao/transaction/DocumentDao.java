package com.github.ioridazo.fundanalyzer.patch.domain.dao.transaction;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.DocTypeCode;
import com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction.Document;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DocumentDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DocumentDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select document by document_id")
    public Document selectByDocumentId(final String documentId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM document WHERE document_id = :documentId",
                new MapSqlParameterSource("documentId", documentId),
                new BeanPropertyRowMapper<>(Document.class)
        );
    }

    @NewSpan("select document by document_type_code")
    public List<Document> selectByDocumentTypeCode(final DocTypeCode docTypeCode) {
        return jdbcTemplate.query(
                "SELECT * FROM document WHERE document_type_code = :documentTypeCode",
                new MapSqlParameterSource("documentTypeCode", docTypeCode.toValue()),
                new BeanPropertyRowMapper<>(Document.class)
        );
    }

    @NewSpan("update document set document_period")
    @Transactional
    public void updateDocumentPeriod(final Document document) {
        jdbcTemplate.update(
                "UPDATE document set document_period = :documentPeriod",
                new MapSqlParameterSource("documentPeriod", document.getDocumentPeriod())
        );
    }
}
