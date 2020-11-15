package br.com.jkassner.apiloteria.service;

import br.com.jkassner.apiloteria.model.TipoLoteria;

import java.util.List;
import java.util.Map;

public interface AbstractConcursoService<T> {
    T findByIdConcurso(Long idConcurso);
    Map<String, List<T>> findConcursosByDezenas(List<Integer> dezenasUsuario);
    T getUltimoConcurso();
    List<T> allConcursos(int page, TipoLoteria tipoLoteria);
}
