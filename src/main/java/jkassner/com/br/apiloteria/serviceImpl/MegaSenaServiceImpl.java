package jkassner.com.br.apiloteria.serviceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
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

	@SuppressWarnings("deprecation")
	@Override
	public ConcursoMegaSena getUltimoConcurso() {
		ConcursoMegaSena ultimoConcurso = megaSenaRepository.findFirstByOrderByDtSorteioDesc();
		Date dtSorteio = ultimoConcurso.getDtSorteio();

		LocalDate data = LocalDate.of(dtSorteio.getYear(), dtSorteio.getMonth(), dtSorteio.getDay());
		LocalDateTime agora = LocalDateTime.now();

		if (concursoEDeHoje(data, agora.toLocalDate())) {
			return ultimoConcurso;
		}

		if (hojeSaiuConcurso(agora)) {
			return boraBuscarConcursoNaNet(ultimoConcurso);
		}

		if (ultimoConcursoOcorreuA4Dias(agora.toLocalDate(), data)) {
			return boraBuscarConcursoNaNet(ultimoConcurso);
		}

		return ultimoConcurso;
	}
	
	public ConcursoMegaSena boraBuscarConcursoNaNet(ConcursoMegaSena ultimoConcurso) {
		String contentFile = downloadTodosConcursosZipMegaSena.download();
		Document doc = Jsoup.parse(contentFile);
        Elements tables = doc.getElementsByTag("table");
        Element table = tables.first();
        Elements td = table.select("td:eq(0):matches("+ ultimoConcurso.getIdConcurso() +")");
        Element tr = td.parents().get(0);
        ConcursoMegaSena concursoMegaSena = parseContentFileServiceImpl
        		.parserTrToConcursoMegaSena(tr.nextElementSibling());// TODO quando tem um rowspan >1 da pau
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Depois gravo no banco id: "+ concursoMegaSena.getIdConcurso());
				megaSenaRepository.save(concursoMegaSena);
			}
		};
		
		new Thread(runnable).start();
		
		System.out.println("Primeiro retorno");
        return concursoMegaSena;
	}

	private boolean ultimoConcursoOcorreuA4Dias(LocalDate dtSorteio, LocalDate hoje) {
		return Period.between(hoje, dtSorteio).getDays() >= 4;
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
