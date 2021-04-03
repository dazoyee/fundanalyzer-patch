package com.github.ioridazo.fundanalyzer.patch.domain.dao.master;

import com.github.ioridazo.fundanalyzer.patch.domain.entity.master.Company;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CompanyDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NewSpan("select company by all")
    public List<Company> selectAll() {
        return jdbcTemplate.query(
                "SELECT * FROM company",
                new BeanPropertyRowMapper<>(Company.class)
        );
    }
}
