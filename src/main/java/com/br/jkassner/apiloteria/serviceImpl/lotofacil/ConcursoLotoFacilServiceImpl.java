package com.br.jkassner.apiloteria.serviceImpl.lotofacil;

import com.br.jkassner.apiloteria.model.ConcursoLotoFacil;
import com.br.jkassner.apiloteria.repository.lotofacil.ConcursoLotoFacilRepository;
import com.br.jkassner.apiloteria.service.DownloadService;
import com.br.jkassner.apiloteria.service.ParserContentFileService;
import com.br.jkassner.apiloteria.service.concurso.ConcursoService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class ConcursoLotoFacilServiceImpl implements ConcursoService<ConcursoLotoFacil> {

	@Autowired
	ConcursoLotoFacilRepository concursoLotoFacilRepository;

	@Autowired
	@Qualifier("downloadTodosConcursosZipLotoFacil")
	DownloadService downloadTodosConcursos;

	@Autowired
	@Qualifier("parseContentFileLotoFacilServiceImpl")
	ParserContentFileService<ConcursoLotoFacil> parseContentFileServiceImpl;

	@Override
	public ConcursoLotoFacil findByIdConcurso(Long idConcurso) {
		return concursoLotoFacilRepository.findByIdConcurso(idConcurso);
	}

	@Override
	public Map<String, List<ConcursoLotoFacil>> findConcursosByDezenas(List<Integer> dezenasUsuario) {
		return null;
	}

	@Override
	public ConcursoLotoFacil getUltimoConcurso() {
		ConcursoLotoFacil ultimoConcursoLocal = concursoLotoFacilRepository.findFirstByOrderByDtSorteioDesc();

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

	public ConcursoLotoFacil boraBuscarConcursoAtual(ConcursoLotoFacil ultimoConcursoLocal) {
		String contentFile = downloadTodosConcursos.download();
		Document doc = Jsoup.parse(contentFile);
		Element tableConcursos = getTableConcursos(doc);
		Element trUltimoConcursoLocal = getTrUltimoConcursoLocal(tableConcursos, ultimoConcursoLocal);
		
		// quando o ultimo concurso ainda continua sendo o da base local
		// demora as vez para atualizar o aqruivo com o concurso mais recente
		if (trUltimoConcursoLocal.nextElementSibling() == null) {
			return ultimoConcursoLocal;
		}
		
		Element trUltimoConcursoWeb = getTrUltimoConcursoWeb(trUltimoConcursoLocal);

		ConcursoLotoFacil concursoLotoFacil = parseContentFileServiceImpl.parserTrToConcurso(trUltimoConcursoWeb,null);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				concursoLotoFacilRepository.save(concursoLotoFacil);
			}
		};

		new Thread(runnable).start();

		return concursoLotoFacil;
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

	private Element getTrUltimoConcursoLocal(Element table, ConcursoLotoFacil ultimoConcursoLocal) {
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
