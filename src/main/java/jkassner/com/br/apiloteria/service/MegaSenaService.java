package jkassner.com.br.apiloteria.service;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.model.ICounterPosicao;

import java.util.List;
import java.util.Map;

public interface MegaSenaService {
    ConcursoMegaSena findByIdConcurso(Long id);
    Map<String, List<ConcursoMegaSena>> findConcursosByDezenas(boolean findSena, boolean findQUina, boolean findQuadra, List<Integer> dezenasUsuario);
    ConcursoMegaSena getUltimoConcurso();
    Map<Long, List<ICounterPosicao>> getCounterPosicoes();
}
