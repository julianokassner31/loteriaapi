package jkassner.com.br.apiloteria.repository.megasena;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import jkassner.com.br.apiloteria.sql.MegaSenaSql;

@Repository
public interface ConcursoMegaSenaRepository extends JpaRepository<ConcursoMegaSena, Long> {

    ConcursoMegaSena findByIdConcurso(Long id);

    @Query(value = MegaSenaSql.FIND_CONCURSOS_BY_ID_DEZENA, nativeQuery = true)
    List<ConcursoMegaSena> findConcursosByDezenas(@Param("primeira") int primeira, @Param("ultima") int ultima);
}
