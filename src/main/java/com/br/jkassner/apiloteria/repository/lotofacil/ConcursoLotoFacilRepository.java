package com.br.jkassner.apiloteria.repository.lotofacil;

import com.br.jkassner.apiloteria.model.ConcursoLotoFacil;
import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import com.br.jkassner.apiloteria.model.ICounterPosicao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConcursoLotoFacilRepository extends JpaRepository<ConcursoLotoFacil, Long> {

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
    List<ICounterPosicao> getCounterPosicaoPrDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.seDezena")
    List<ICounterPosicao> getCounterPosicaoSeDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.teDezena")
    List<ICounterPosicao> getCounterPosicaoTeDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.qaDezena")
    List<ICounterPosicao> getCounterPosicaoQaDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.qiDezena")
    List<ICounterPosicao> getCounterPosicaoQiDezena(Pageable pageable);
    
    @Query(name = "CounterPosicao.sxDezena")
    List<ICounterPosicao> getCounterPosicaoSxDezena(Pageable pageable);
}
