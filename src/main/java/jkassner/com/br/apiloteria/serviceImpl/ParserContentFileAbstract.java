package jkassner.com.br.apiloteria.serviceImpl;

import java.math.BigDecimal;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jkassner.com.br.apiloteria.service.ParserContentFileService;

public abstract class ParserContentFileAbstract implements ParserContentFileService {
   
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
