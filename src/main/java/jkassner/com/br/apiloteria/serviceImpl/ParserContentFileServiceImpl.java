package jkassner.com.br.apiloteria.serviceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.exception.DataException;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jkassner.com.br.apiloteria.model.Cidade;
import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.model.DezenasMegaSenaOrdenadas;
import jkassner.com.br.apiloteria.repository.MegaSenaRepository;
import jkassner.com.br.apiloteria.service.DownloadService;

@Service("parseContentFileServiceImpl")
public class ParserContentFileServiceImpl extends ParserContentFileAbstract {

    @Autowired
    MegaSenaRepository concursoMegaSenaRepository;
    
    @Autowired
    DownloadService downloadTodosConcursosZipMegaSena;

    @Override
    public void populaResultados() throws IOException {
        parserContentFile();
    }

    public void parserContentFile() throws FileNotFoundException {
        
        String contentFile = downloadTodosConcursosZipMegaSena.download();
        Document doc = Jsoup.parse(contentFile);
        Element body = doc.body();
        Elements tables = body.getElementsByTag("table");
        Element table = tables.first();
        Elements trs = table.getElementsByTag("tr");
        Iterator<Element> iterator = trs.iterator();

        // tr com os dados do sorteios
        while (iterator.hasNext()) {
            try {

                Element trDadosConcurso = iterator.next();
                Elements concurso = trDadosConcurso.getElementsContainingText("Concurso");

                // primeira tr vem os th, pulo
                if (!concurso.isEmpty()) continue;
                
                ConcursoMegaSena concursoMegaSena = parserTrToConcursoMegaSena(trDadosConcurso, iterator);
                
                concursoMegaSenaRepository.save(concursoMegaSena);

            }catch(DataException dataException) {
                System.out.println("Erro ao executar sql: " + dataException.getSQL());
                continue;

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
    
    @Override
    public ConcursoMegaSena parserTrToConcursoMegaSena(Element trDadosConcurso, Iterator<Element> iterator) {

        Elements tdsDados = trDadosConcurso.getElementsByTag("td");
        ConcursoMegaSena concursoMegaSena = new ConcursoMegaSena();
        Element nrConcurso = tdsDados.get(0);
        long id = Long.parseLong(nrConcurso.text());
        concursoMegaSena.setIdConcurso(id);
        Element dtSorteio = tdsDados.get(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
			data = simpleDateFormat.parse(dtSorteio.text());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        concursoMegaSena.setDtSorteio(data);
        setDezenas(concursoMegaSena, tdsDados);
        concursoMegaSena.setVlArrecadacaoTotal(converterToBigDecimal(8, tdsDados));
        concursoMegaSena.setNrGanhadoresSena(converterToInt(9, tdsDados));
        Element elCidade = tdsDados.get(10);
        Element elUf = tdsDados.get(11);
        addCidade(concursoMegaSena, elCidade, elUf);
        concursoMegaSena.setVlRateioSena(converterToBigDecimal(12, tdsDados));
        concursoMegaSena.setNrGanhadoresQuina(converterToInt(13, tdsDados));
        concursoMegaSena.setVlRateioQuina(converterToBigDecimal(14, tdsDados));
        concursoMegaSena.setNrGanhadoresQuadra(converterToInt(15, tdsDados));
        concursoMegaSena.setVlRateioQuadra(converterToBigDecimal(16, tdsDados));
        concursoMegaSena.setAcumulado(tdsDados.get(17).text().equals("SIM"));
        concursoMegaSena.setVlAcumulado(converterToBigDecimal(18, tdsDados));
        concursoMegaSena.setVlEstimativaPremio(converterToBigDecimal(19, tdsDados));
        concursoMegaSena.setVlAcumuladoMegaVirada(converterToBigDecimal(20, tdsDados));
        
        int nrRowspan = Integer.parseInt(tdsDados.get(0).attributes().get("rowspan"));
        if (nrRowspan > 1) {

            // criado porque la encima ja contei uma tr
            int count = nrRowspan - 1;
            for (int i = 0; i < count; i++) {

                // tr com as cidades vem separado do concurso
                Element trCidades = iterator != null ? iterator.next() : trDadosConcurso.nextElementSibling();
                Elements tdsCidadesUf = trCidades.getElementsByTag("td");
                elCidade = tdsCidadesUf.get(0);
                elUf = tdsCidadesUf.get(1);
                
                addCidade(concursoMegaSena, elCidade, elUf);
            }
        }
        
        return concursoMegaSena;
    }

    private void setDezenas(ConcursoMegaSena concursoMegaSena, Elements tdsDados) {

        int primeira = converterToInt(2, tdsDados);
        concursoMegaSena.setPrDezena(primeira);

        int segunda = converterToInt(3, tdsDados);
        concursoMegaSena.setSeDezena(segunda);

        int terceira = converterToInt(4, tdsDados);
        concursoMegaSena.setTeDezena(terceira);

        int quarta = converterToInt(5, tdsDados);
        concursoMegaSena.setQaDezena(quarta);

        int quinta = converterToInt(6, tdsDados);
        concursoMegaSena.setQiDezena(quinta);

        int sexta = converterToInt(7, tdsDados);
        concursoMegaSena.setSxDezena(sexta);

        List<Integer> listaDezenas = Arrays.asList(primeira, segunda, terceira, quarta, quinta, sexta);

        setDenasOrdenadas(listaDezenas, concursoMegaSena);
    }

    private void setDenasOrdenadas(List<Integer> listaDezenas, ConcursoMegaSena concursoMegaSena) {
        listaDezenas.sort(Comparator.naturalOrder());

        DezenasMegaSenaOrdenadas dezenasMegaSenaOrdenadas = new DezenasMegaSenaOrdenadas();
        dezenasMegaSenaOrdenadas.setPrimeira(listaDezenas.get(0));
        dezenasMegaSenaOrdenadas.setSegunda(listaDezenas.get(1));
        dezenasMegaSenaOrdenadas.setTerceira(listaDezenas.get(2));
        dezenasMegaSenaOrdenadas.setQuarta(listaDezenas.get(3));
        dezenasMegaSenaOrdenadas.setQuinta(listaDezenas.get(4));
        dezenasMegaSenaOrdenadas.setSexta(listaDezenas.get(5));

        dezenasMegaSenaOrdenadas.setConcursoMegaSena(concursoMegaSena);

        concursoMegaSena.setDezenasMegaSenaOrdenadas(dezenasMegaSenaOrdenadas);
    }

    private void addCidade(ConcursoMegaSena concursoMegaSena, Element elCidade, Element elUf) {
        String nmCidade = elCidade.text().replace("&nbsp;", "").trim();
        String uf = elUf.text().replace("&nbsp;", "").trim();

        if (!StringUtil.isBlank(nmCidade) || !StringUtil.isBlank(uf)) {
            Cidade cidade = new Cidade();
            cidade.setNmCIdade(nmCidade.toUpperCase());
            cidade.setUf(uf.toUpperCase());
            cidade.setConcursoMegaSena(concursoMegaSena);
            concursoMegaSena.getCidades().add(cidade);
        }
    }
    
    public static void main(String[] args) {
    	String html = "<table>"
				+ "<tr rowpsan='2'><td>1900</td></tr>"
				+ "<tr><td>1902</td><td>1900</td></tr>"
				+ "<tr><td>1901</td></tr>"
				+ "</table>";
		
		Document doc = Jsoup.parse(html);
        Elements tables = doc.getElementsByTag("table");
        Element table = tables.first();
        Elements td = table.select("td:eq(0):matches(1900)");
        Element tr = td.parents().get(0);
       
        System.out.println(tr.nextElementSibling());
	}
    
}


