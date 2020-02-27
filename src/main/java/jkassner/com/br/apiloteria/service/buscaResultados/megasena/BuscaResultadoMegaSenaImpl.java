package jkassner.com.br.apiloteria.service.buscaResultados.megasena;

import jkassner.com.br.apiloteria.model.Cidade;
import jkassner.com.br.apiloteria.model.DezenasMegaSenaOrdenadas;
import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.repository.megasena.ConcursoMegaSenaRepository;
import jkassner.com.br.apiloteria.service.buscaResultados.BuscaResultadoAbstract;
import jkassner.com.br.apiloteria.service.buscaResultados.TipoLoteria;
import org.hibernate.exception.DataException;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("buscaResultadoMegaSenaService")
public class BuscaResultadoMegaSenaImpl extends BuscaResultadoAbstract {

    @Autowired
    ConcursoMegaSenaRepository concursoMegaSenaRepository;

    BuscaResultadoMegaSenaImpl() {
        this.tipoLoteria = TipoLoteria.MEGASENA;
    }

    @Override
    public void populaResultados() throws IOException {
        baixaResultados();
        unzipArquivosBaixados();
        parserContentFile();
    }

    public void parserContentFile() throws FileNotFoundException {
        
        String contentFile = getContentFile();
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

                Elements tdsDados = trDadosConcurso.getElementsByTag("td");

                ConcursoMegaSena concursoMegaSena = new ConcursoMegaSena();

                Element nrConcurso = tdsDados.get(0);
                int nrRowspan = Integer.parseInt(tdsDados.get(0).attributes().get("rowspan"));

                long id = Long.parseLong(nrConcurso.text());
                concursoMegaSena.setIdConcurso(id);

                Element dtSorteio = tdsDados.get(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date data = simpleDateFormat.parse(dtSorteio.text());
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

                if (nrRowspan > 1) {

                    // criado porque la encima ja contei uma tr
                    int count = nrRowspan - 1;
                    for (int i = 0; i < count; i++) {

                        // tr com as cidades vem separado do concurso
                        Element trCidades = iterator.next();
                        Elements tdsCidadesUf = trCidades.getElementsByTag("td");

                        elCidade = tdsCidadesUf.get(0);
                        elUf = tdsCidadesUf.get(1);
                        addCidade(concursoMegaSena, elCidade, elUf);
                    }
                }

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
}


