package com.br.jkassner.apiloteria.repository.megasena;

import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import com.br.jkassner.apiloteria.service.counterposicao.CounterPosicaoService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConcursoMegaSenaRepository extends JpaRepository<ConcursoMegaSena, Long> {

    ConcursoMegaSena findByIdConcurso(Long idConcurso);

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
    
    ConcursoMegaSena findFirstByOrderByDtSorteioDesc();
    
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
