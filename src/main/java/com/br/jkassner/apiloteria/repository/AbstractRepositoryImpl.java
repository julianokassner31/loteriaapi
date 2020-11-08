package com.br.jkassner.apiloteria.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/*
 * @created 07/11/2020 - 19:24
 * @project api-loteria
 * @author Juliano Kassner
 */
@Getter
public abstract class AbstractRepositoryImpl<T> implements AbstractRepository<T> {

    @Autowired
    JdbcTemplate jdbcTemplate;
}
