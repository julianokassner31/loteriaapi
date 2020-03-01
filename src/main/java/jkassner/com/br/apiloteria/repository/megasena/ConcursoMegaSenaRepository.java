package jkassner.com.br.apiloteria.repository.megasena;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
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
    List<ConcursoMegaSena> findQuinas(
        @Param("primeira") int primeira,
        @Param("segunda") int segunda,
        @Param("terceira") int terceira,
        @Param("quarta") int quarta,
        @Param("quinta") int quinta,
        @Param("sexta") int sexta
    );

//    @Query(name = "ConcursoMegaSena.findQuadras")
//    List<ConcursoMegaSena> findQuadras(
//            @Param("primeira") int primeira,
//            @Param("segunda") int segunda,
//            @Param("terceira") int terceira,
//            @Param("quarta") int quarta,
//            @Param("quinta") int quinta,
//            @Param("sexta") int sexta
//    );
}
