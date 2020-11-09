package com.br.jkassner.apiloteria.repository.abstractconcurso;

import com.br.jkassner.apiloteria.repository.abstractjdbctemplate.AbstractJdbcTemplateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/*
 * @created 08/11/2020 - 17:45
 * @project api-loteria
 * @author Juliano Kassner
 */
@NoRepositoryBean
public interface AbstractConcursoRepository<T> extends JpaRepository<T, Long>, AbstractJdbcTemplateRepository {
    T findByIdConcurso(Long idConcurso);
    T findFirstByOrderByDtSorteioDesc();
}
