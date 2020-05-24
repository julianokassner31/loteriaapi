package jkassner.com.br.apiloteria.controller.megasena;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.sentry.SentryClient;
import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.model.ICounterPosicao;
import jkassner.com.br.apiloteria.repository.MegaSenaRepository;
import jkassner.com.br.apiloteria.service.MegaSenaService;
import jkassner.com.br.apiloteria.service.ParserContentFileService;

@RestController
@RequestMapping("/megasena")
public class MegaSenaController {

	@Autowired
	MegaSenaService megaSenaService;

	@Autowired
	MegaSenaRepository megaSenaRepository;

	@Autowired
	ParserContentFileService parseContentFileServiceImpl;

	@Autowired
	SentryClient sentryClient;

	@GetMapping("/{idConcurso}")
	public ResponseEntity<?> getConcurso(@PathVariable("idConcurso") Long idConcurso) throws Exception {
		ConcursoMegaSena concursoMegaSena = megaSenaService.findByIdConcurso(idConcurso);

		return ResponseEntity.ok(concursoMegaSena);
	}

	@GetMapping("/find-concursos")
	public ResponseEntity<?> findConcursos(@RequestParam(value = "dezenasUsuario") List<Integer> dezenasUsuario) {

		sentryClient.sendMessage("Iniciando busca por concursos premiados");

		Map<String, List<ConcursoMegaSena>> concursosByDezenas = megaSenaService.findConcursosByDezenas(true, true,
				true, dezenasUsuario);

		if (concursosByDezenas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(concursosByDezenas);
	}

	@GetMapping("/populaResultados")
	public ResponseEntity<?> populaResultados() throws IOException {
		parseContentFileServiceImpl.populaResultados();

		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<?> buscaUltimoConcurso() {

		ConcursoMegaSena concurso = megaSenaService.getUltimoConcurso();
		CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);
		return ResponseEntity.ok().cacheControl(cacheControl).body(concurso);
	}
	
	@GetMapping("/counter-posicoes")
	public ResponseEntity<?> getCounterPosicao(@RequestParam("page") int page) {

		Map<Long, List<ICounterPosicao>> counterPosicoes = megaSenaService.getCounterPosicoes(page);
		CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);
		return ResponseEntity.ok().cacheControl(cacheControl).body(counterPosicoes);
	}
}
