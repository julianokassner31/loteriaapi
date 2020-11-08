package com.br.jkassner.apiloteria.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import com.br.jkassner.apiloteria.service.DownloadService;
import org.hibernate.exception.DataException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.br.jkassner.apiloteria.service.ParserContentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ParserContentFileAbstract<T> implements ParserContentFileService<T> {

    public abstract JpaRepository getRepository();

    public void downloaAndSaveConcursos() {

        String contentFile = download();
        Document doc = Jsoup.parse(contentFile);
        Element body = doc.body();
        Elements tables = body.getElementsByTag("table");
        Element table = tables.first();
        Elements trs = table.getElementsByTag("tr");
        Iterator<Element> iterator = trs.iterator();


        while (iterator.hasNext()) {
            try {

                // tr com os dados do sorteios
                Element trDadosConcurso = iterator.next();

                Elements concursoEl = trDadosConcurso.getElementsContainingText("Concurso");

                // primeira tr vem os th, pulo
                if (!concursoEl.isEmpty()) continue;

                T concurso = parserTrToConcurso(trDadosConcurso, iterator);

                getRepository().save(concurso);

            } catch(DataException dataException) {
                System.out.println("Erro ao executar sql: " + dataException.getSQL());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected int converterToInt(int pos, Elements td) {
        Element prDezena = td.get(pos);
        return Integer.parseInt(prDezena.text());
    }

    protected BigDecimal converterToBigDecimal(int pos, Elements td) {
        Element prDezena = td.get(pos);
        String replace = prDezena.text().replace(".", "").replace(",", ".");
        return new BigDecimal(replace);
    }
}
