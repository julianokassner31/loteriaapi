package com.br.jkassner.apiloteria.service;

import java.util.List;
import java.util.Map;

public interface AbstractConcursoService<T> {
    T findByIdConcurso(Long idConcurso);
    Map<String, List<T>> findConcursosByDezenas(List<Integer> dezenasUsuario);
    T getUltimoConcurso();
}
