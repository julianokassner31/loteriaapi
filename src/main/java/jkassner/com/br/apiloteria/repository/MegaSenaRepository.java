package jkassner.com.br.apiloteria.repository;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.model.ICounterPosicao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MegaSenaRepository extends JpaRepository<ConcursoMegaSena, Long> {

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
    List<ICounterPosicao> getCounterPosicaoPrDezena();
    
    @Query(name = "CounterPosicao.seDezena")
    List<ICounterPosicao> getCounterPosicaoSeDezena();
    
    @Query(name = "CounterPosicao.teDezena")
    List<ICounterPosicao> getCounterPosicaoTeDezena();
    
    @Query(name = "CounterPosicao.qaDezena")
    List<ICounterPosicao> getCounterPosicaoQaDezena();
    
    @Query(name = "CounterPosicao.qiDezena")
    List<ICounterPosicao> getCounterPosicaoQiDezena();
    
    @Query(name = "CounterPosicao.sxDezena")
    List<ICounterPosicao> getCounterPosicaoSxDezena();
}
