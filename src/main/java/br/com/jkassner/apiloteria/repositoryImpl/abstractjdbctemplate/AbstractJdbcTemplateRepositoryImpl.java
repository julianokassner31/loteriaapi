package br.com.jkassner.apiloteria.repositoryImpl.abstractjdbctemplate;

import br.com.jkassner.apiloteria.repository.abstractjdbctemplate.AbstractJdbcTemplateRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/*
 * @created 08/11/2020 - 17:57
 * @project api-loteria
 * @author Juliano Kassner
 */
@Getter
public abstract class AbstractJdbcTemplateRepositoryImpl implements AbstractJdbcTemplateRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

}
