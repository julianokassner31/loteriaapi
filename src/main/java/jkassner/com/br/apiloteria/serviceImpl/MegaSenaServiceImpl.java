package jkassner.com.br.apiloteria.serviceImpl;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.repository.MegaSenaRepository;
import jkassner.com.br.apiloteria.service.DownloadService;
import jkassner.com.br.apiloteria.service.MegaSenaService;
import jkassner.com.br.apiloteria.service.ParserContentFileService;

@Service
public class MegaSenaServiceImpl implements MegaSenaService {

	@Autowired
	MegaSenaRepository megaSenaRepository;

	@Autowired
	DownloadService downloadTodosConcursosZipMegaSena;

	@Autowired
	ParserContentFileService parseContentFileServiceImpl;

	@Override
	public ConcursoMegaSena findByIdConcurso(Long idConcurso) {
		return megaSenaRepository.findByIdConcurso(idConcurso);
	}

	@Override
	public Map<String, List<ConcursoMegaSena>> findConcursosByDezenas(boolean findSena, boolean findQuina,
			boolean findQuadra, List<Integer> dezenasUsuario) {

		dezenasUsuario.sort(Comparator.naturalOrder());
		Integer primeira = dezenasUsuario.get(0);
		Integer segunda = dezenasUsuario.get(1);
		Integer terceira = dezenasUsuario.get(2);
		Integer quarta = dezenasUsuario.get(3);
		Integer quinta = dezenasUsuario.get(4);
		Integer sexta = dezenasUsuario.get(5);

		Map<String, List<ConcursoMegaSena>> concursosMap = new HashMap<>();

		List<ConcursoMegaSena> senas = new ArrayList<ConcursoMegaSena>();

		if (findSena) {
			senas = megaSenaRepository.findSenas(primeira, segunda, terceira, quarta, quinta, sexta);

			if (!senas.isEmpty()) {
				concursosMap.put("senas", senas);
			}
		}

		if (senas.isEmpty()) {

			List<ConcursoMegaSena> possiveisQuinasQuadras = megaSenaRepository.findPossiveisQuinasEQuadras(primeira,
					segunda, terceira, quarta, quinta, sexta);

			if (findQuina) {
				List<ConcursoMegaSena> quinas = getConcursosQuinasEQuadras(true, possiveisQuinasQuadras, primeira,
						segunda, terceira, quarta, quinta, sexta);
				possiveisQuinasQuadras.removeAll(quinas);

				if (!quinas.isEmpty()) {
					concursosMap.put("quinas", quinas);
				}
			}

			if (findQuadra) {
				List<ConcursoMegaSena> quadras = getConcursosQuinasEQuadras(false, possiveisQuinasQuadras, primeira,
						segunda, terceira, quarta, quinta, sexta);

				if (!quadras.isEmpty()) {
					concursosMap.put("quadras", quadras);
				}
			}
		}

		return concursosMap;
	}

	private List<ConcursoMegaSena> getConcursosQuinasEQuadras(boolean findQuina, List<ConcursoMegaSena> possiveisQuinas,
			Integer primeira, Integer segunda, Integer terceira, Integer quarta, Integer quinta, Integer sexta) {

		return possiveisQuinas.stream().filter(concurso -> {

			List<Integer> listDezenas = Arrays.asList(concurso.getPrDezena(), concurso.getSeDezena(),
					concurso.getTeDezena(), concurso.getQaDezena(), concurso.getQiDezena(), concurso.getSxDezena());

			int count = 0;

			if (listDezenas.contains(primeira)) {
				count++;
			}

			if (listDezenas.contains(segunda)) {
				count++;
			}

			if (listDezenas.contains(terceira)) {
				count++;
			}

			if (listDezenas.contains(quarta)) {
				count++;
			}

			if (listDezenas.contains(quinta)) {
				count++;
			}

			if (listDezenas.contains(sexta)) {
				count++;
			}

			return findQuina ? count == 5 : count == 4;

		}).collect(Collectors.toList());
	}

	@Override
	public ConcursoMegaSena getUltimoConcurso() {
		ConcursoMegaSena ultimoConcursoLocal = megaSenaRepository.findFirstByOrderByDtSorteioDesc();

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

	public ConcursoMegaSena boraBuscarConcursoAtual(ConcursoMegaSena ultimoConcursoLocal) {
		String contentFile = downloadTodosConcursosZipMegaSena.download();
		Document doc = Jsoup.parse(contentFile);
		Element tableConcursos = getTableConcursos(doc);
		Element trUltimoConcursoLocal = getTrUltimoConcursoLocal(tableConcursos, ultimoConcursoLocal);
		
		// quando o ultimo concurso ainda continua sendo o da base local
		// demora as vez para atualizar o aqruivo com o concurso mais recente
		if (trUltimoConcursoLocal.nextElementSibling() == null) {
			return ultimoConcursoLocal;
		}
		
		Element trUltimoConcursoWeb = getTrUltimoConcursoWeb(trUltimoConcursoLocal);

		ConcursoMegaSena concursoMegaSena = parseContentFileServiceImpl.parserTrToConcursoMegaSena(trUltimoConcursoWeb,
				null);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				megaSenaRepository.save(concursoMegaSena);
			}
		};

		new Thread(runnable).start();

		return concursoMegaSena;
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

	private Element getTrUltimoConcursoLocal(Element table, ConcursoMegaSena ultimoConcursoLocal) {
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

		return hojeTem && passouDas21H; // Pau
	}
}
