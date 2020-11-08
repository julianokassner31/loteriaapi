package com.br.jkassner.apiloteria.service.concurso;

import com.br.jkassner.apiloteria.service.AbstractService;

import java.util.List;
import java.util.Map;

public interface ConcursoService<T> extends AbstractService<T> {

    T findByIdConcurso(Long idConcurso);

    Map<String, List<T>> findConcursosByDezenas(List<Integer> dezenasUsuario);

    T getUltimoConcurso();
}
