package com.br.jkassner.apiloteria.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
 * @created 07/11/2020 - 19:24
 * @project api-loteria
 * @author Juliano Kassner
 */

@Repository
public interface AbstractRepository<T> {

    JdbcTemplate getJdbcTemplate();
}
