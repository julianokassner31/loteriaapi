package jkassner.com.br.apiloteria.service.megasena;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;

import java.util.List;
import java.util.Map;

public interface ConcursoMegaSenaService {
    ConcursoMegaSena findByIdConcurso(Long id);
    Map<String, List<ConcursoMegaSena>> findConcursosByDezenas(boolean findSena, boolean findQUina, boolean findQuadra, List<Integer> dezenasUsuario);
}
