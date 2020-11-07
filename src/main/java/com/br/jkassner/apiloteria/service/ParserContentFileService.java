package com.br.jkassner.apiloteria.service;

import java.io.IOException;
import java.util.Iterator;

import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import org.jsoup.nodes.Element;

public interface ParserContentFileService<T> {

    public void populaResultados() throws IOException;
    public T parserTrToConcurso(Element trDadosConcurso, Iterator<Element> iterator);
    public String download();
}
