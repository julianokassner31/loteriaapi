package com.br.jkassner.apiloteria.repository.megasena;

import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import com.br.jkassner.apiloteria.repository.abstractconcurso.AbstractConcursoRepository;
import com.br.jkassner.apiloteria.service.counterposicao.CounterPosicaoService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConcursoMegaSenaRepository extends AbstractConcursoRepository<ConcursoMegaSena> {

    @Query(name = "ConcursoMegaSena.findSenas")
    List<ConcursoMegaSena> findSenas(
        @Param("primeira") int primeira,
        @Param("segunda") int segunda,
        @Param("terceira") int terceira,
        @Param("quarta") int quarta,
        @Param("quinta") int quinta,
        @Param("sexta") int sexta
    );

    @Query(name = "ConcursoMegaSena.findQuinas")
    List<ConcursoMegaSena> findPossiveisQuinasEQuadras(
        @Param("primeira") int primeira,
        @Param("segunda") int segunda,
        @Param("terceira") int terceira,
        @Param("quarta") int quarta,
        @Param("quinta") int quinta,
        @Param("sexta") int sexta
    );
}
