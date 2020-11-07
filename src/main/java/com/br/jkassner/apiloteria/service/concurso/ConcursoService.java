package com.br.jkassner.apiloteria.service.concurso;

import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import com.br.jkassner.apiloteria.model.ICounterPosicao;

import java.util.List;
import java.util.Map;

public interface ConcursoService<T> {
    T findByIdConcurso(Long id);
    Map<String, List<T>> findConcursosByDezenas(boolean findSena, boolean findQUina, boolean findQuadra, List<Integer> dezenasUsuario);
    T getUltimoConcurso();
	Map<Long, List<ICounterPosicao>> getCounterPosicoes(int page);
}
