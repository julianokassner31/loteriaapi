package jkassner.com.br.apiloteria.service;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.nodes.Element;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;

public interface ParserContentFileService {

    public void populaResultados() throws IOException;
    public ConcursoMegaSena parserTrToConcursoMegaSena(Element trDadosConcurso, Iterator<Element> iterator);
}
