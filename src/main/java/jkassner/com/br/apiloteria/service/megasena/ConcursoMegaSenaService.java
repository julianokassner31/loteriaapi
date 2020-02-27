package jkassner.com.br.apiloteria.service.megasena;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;

import java.util.List;

public interface ConcursoMegaSenaService {
    ConcursoMegaSena findByIdConcurso(Long id);
    List<ConcursoMegaSena> findConcursosByDezenas(List<Integer> dezenasUsuario);
}
