package com.br.jkassner.apiloteria.repository.lotofacil;

import com.br.jkassner.apiloteria.model.ConcursoLotoFacil;
import com.br.jkassner.apiloteria.repository.AbstractRepository;
import com.br.jkassner.apiloteria.service.counterposicao.CounterPosicaoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcursoLotoFacilRepository extends AbstractRepository<ConcursoLotoFacil>, JpaRepository<ConcursoLotoFacil, Long> {

    ConcursoLotoFacil findByIdConcurso(Long idConcurso);

    @Query(name = "ConcursoMegaSena.findSenas")
    List<ConcursoLotoFacil> findSenas(
            @Param("primeira") int primeira,
            @Param("segunda") int segunda,
            @Param("terceira") int terceira,
            @Param("quarta") int quarta,
            @Param("quinta") int quinta,
            @Param("sexta") int sexta
    );

    @Query(name = "ConcursoMegaSena.findQuinas")
    List<ConcursoLotoFacil> findPossiveisQuinasEQuadras(
            @Param("primeira") int primeira,
            @Param("segunda") int segunda,
            @Param("terceira") int terceira,
            @Param("quarta") int quarta,
            @Param("quinta") int quinta,
            @Param("sexta") int sexta
    );

    ConcursoLotoFacil findFirstByOrderByDtSorteioDesc();
    
    @Query(name = "CounterPosicao.prDezena")
    List<CounterPosicaoService> getCounterPosicaoPrDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.seDezena")
    List<CounterPosicaoService> getCounterPosicaoSeDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.teDezena")
    List<CounterPosicaoService> getCounterPosicaoTeDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.qaDezena")
    List<CounterPosicaoService> getCounterPosicaoQaDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.qiDezena")
    List<CounterPosicaoService> getCounterPosicaoQiDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.sxDezena")
    List<CounterPosicaoService> getCounterPosicaoSxDezena(Pageable pageable);
}
