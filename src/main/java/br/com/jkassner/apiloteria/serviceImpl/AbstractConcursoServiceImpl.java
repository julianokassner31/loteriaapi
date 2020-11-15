package br.com.jkassner.apiloteria.serviceImpl;

import br.com.jkassner.apiloteria.model.Concurso;
import br.com.jkassner.apiloteria.model.TipoLoteria;
import br.com.jkassner.apiloteria.repository.abstractconcurso.AbstractConcursoRepository;
import br.com.jkassner.apiloteria.service.AbstractConcursoService;
import br.com.jkassner.apiloteria.service.DownloadService;
import br.com.jkassner.apiloteria.service.ParserContentFileService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @created 08/11/2020 - 02:38
 * @project api-loteria
 * @author Juliano Kassner
 */
public abstract class AbstractConcursoServiceImpl<T extends Concurso> implements AbstractConcursoService<T> {

   AbstractConcursoRepository<T> repository;

    DownloadService downloadService;

    ParserContentFileService<T> parserContentFileService;

    public AbstractConcursoServiceImpl(
            AbstractConcursoRepository<T> repository,
            DownloadService downloadService,
            ParserContentFileService<T> parserContentFileService
    ) {
        this.repository = repository;
        this.downloadService = downloadService;
        this.parserContentFileService = parserContentFileService;
    }

    @Override
    public T findByIdConcurso(Long idConcurso) {
        return repository.findByIdConcurso(idConcurso);
    }

    @Override
    public Map<String, List<T>> findConcursosByDezenas(List<Integer> dezenasUsuario) {
        return null;
    }

    @Override
    public T getUltimoConcurso() {
        T ultimoConcursoLocal = repository.findFirstByOrderByDtSorteioDesc();

        LocalDate dataConcurso = Instant.ofEpochMilli(ultimoConcursoLocal.getDtSorteio().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDateTime agora = LocalDateTime.now();

        if (concursoEDeHoje(dataConcurso, agora.toLocalDate())) {
            return ultimoConcursoLocal;
        }

        if (hojeSaiuConcurso(agora)) {
            return boraBuscarConcursoAtual(ultimoConcursoLocal);
        }

        if (ultimoConcursoOcorreuA4Dias(dataConcurso, agora.toLocalDate())) {
            return boraBuscarConcursoAtual(ultimoConcursoLocal);
        }

        return ultimoConcursoLocal;
    }

    @Override
    public List<T> allConcursos(int page, TipoLoteria tpLoteria) {
        Sort sort = Sort.by(Sort.Direction.DESC, "idConcurso");
        PageRequest of = PageRequest.of(page, 10, sort);

       return repository.findAll(of).stream().collect(Collectors.toList());
    }

    public T boraBuscarConcursoAtual(T ultimoConcursoLocal) {
        String contentFile = downloadService.download();
        Document doc = Jsoup.parse(contentFile);
        Element tableConcursos = getTableConcursos(doc);
        Element trUltimoConcursoLocal = getTrUltimoConcursoLocal(tableConcursos, ultimoConcursoLocal);

        // quando o ultimo concurso ainda continua sendo o da base local
        // demora as vez para atualizar o aqruivo com o concurso mais recente
        if (trUltimoConcursoLocal.nextElementSibling() == null) {
            return ultimoConcursoLocal;
        }

        Element trUltimoConcursoWeb = getTrUltimoConcursoWeb(trUltimoConcursoLocal);

        T concurso = parserContentFileService.parserTrToConcurso(trUltimoConcursoWeb, null);

        Runnable runnable = () -> repository.save(concurso);

        new Thread(runnable).start();

        return concurso;
    }

    private Element getTrUltimoConcursoWeb(Element trUltimoConcursoLocal) {

        Element trUltimoConcursoWeb = null;
        Element trPossivelUltimoConcursoWeb = trUltimoConcursoLocal.nextElementSibling();// aqui pode vir trs com
        // cidades do concurso local

        while (trUltimoConcursoWeb == null) {

            Elements tdPossivelUltimoConcursoWeb = trPossivelUltimoConcursoWeb.select("td:eq(0):matches(\\d)");

            if (tdPossivelUltimoConcursoWeb != null && !tdPossivelUltimoConcursoWeb.isEmpty()) {
                trUltimoConcursoWeb = trPossivelUltimoConcursoWeb;
                break;
            }

            trPossivelUltimoConcursoWeb = trPossivelUltimoConcursoWeb.nextElementSibling();

            tdPossivelUltimoConcursoWeb = trPossivelUltimoConcursoWeb.select("td:eq(0):matches(\\d)");

            if (tdPossivelUltimoConcursoWeb != null && !tdPossivelUltimoConcursoWeb.isEmpty()) {
                trUltimoConcursoWeb = tdPossivelUltimoConcursoWeb.parents().get(0);
            }
        }

        return trUltimoConcursoWeb;
    }

    private Element getTableConcursos(Document doc) {
        Elements tables = doc.getElementsByTag("table");

        return tables.first();
    }

    private Element getTrUltimoConcursoLocal(Element table, T ultimoConcursoLocal) {
        Elements tdUltimoConcursoLocal = table.select("td:eq(0):matches(" + ultimoConcursoLocal.getIdConcurso() + ")");

        return tdUltimoConcursoLocal.parents().get(0);
    }

    //TODO arrumar para quand nao consegue buscar resultado no dia do concurso (o mais recente)
    //nao da pra esperar mais de 3 dias para fazer a busca do mais recente
    private boolean ultimoConcursoOcorreuA4Dias(LocalDate dataConcurso, LocalDate hoje) {
        Period periodo = Period.between(dataConcurso, hoje);

        return periodo.getMonths() > 0 || periodo.getDays() >= 3;
    }

    private boolean concursoEDeHoje(LocalDate dataConcurso, LocalDate hoje) {

        return dataConcurso.isEqual(hoje);
    }

    private boolean hojeSaiuConcurso(LocalDateTime agora) {
        DayOfWeek hoje = agora.getDayOfWeek();
        boolean hojeTem = hoje.equals(DayOfWeek.SATURDAY) || hoje.equals(DayOfWeek.WEDNESDAY);
        boolean passouDas21H = agora.getHour() > 21;

        return hojeTem && passouDas21H;
    }
}
