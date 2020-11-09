package com.br.jkassner.apiloteria.repository.abstractjdbctemplate;

import org.springframework.jdbc.core.JdbcTemplate;

/*
 * @created 08/11/2020 - 17:57
 * @project api-loteria
 * @author Juliano Kassner
 */
public interface AbstractJdbcTemplateRepository {

    JdbcTemplate getJdbcTemplate();
}
