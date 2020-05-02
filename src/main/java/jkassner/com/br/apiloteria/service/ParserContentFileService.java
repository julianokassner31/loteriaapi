package jkassner.com.br.apiloteria.service;

import java.io.IOException;

import org.jsoup.nodes.Element;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;

public interface ParserContentFileService {

    public void populaResultados() throws IOException;
    public ConcursoMegaSena parserTrToConcursoMegaSena(Element trDadosConcurso);
}
