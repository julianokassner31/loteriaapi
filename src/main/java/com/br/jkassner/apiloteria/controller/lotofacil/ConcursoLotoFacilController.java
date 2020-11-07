package com.br.jkassner.apiloteria.controller.lotofacil;

import com.br.jkassner.apiloteria.model.ConcursoLotoFacil;
import com.br.jkassner.apiloteria.model.ICounterPosicao;
import com.br.jkassner.apiloteria.service.ParserContentFileService;
import com.br.jkassner.apiloteria.service.concurso.ConcursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/lotofacil")
public class ConcursoLotoFacilController {

	@Autowired
	@Qualifier("concursoLotoFacilServiceImpl")
	ConcursoService<ConcursoLotoFacil> concursoService;

	@Autowired
	@Qualifier("parseContentFileLotoFacilServiceImpl")
	ParserContentFileService<ConcursoLotoFacil> parseContentFileServiceImpl;


	@GetMapping("/{idConcurso}")
	public ResponseEntity<?> getConcurso(@PathVariable("idConcurso") Long idConcurso) throws Exception {
		ConcursoLotoFacil concursoLotoFacil = concursoService.findByIdConcurso(idConcurso);

		return ResponseEntity.ok(concursoLotoFacil);
	}

	@GetMapping("/find-concursos")
	public ResponseEntity<?> findConcursos(@RequestParam(value = "dezenasUsuario") List<Integer> dezenasUsuario) {

		Map<String, List<ConcursoLotoFacil>> concursosByDezenas = concursoService.findConcursosByDezenas(true, true,
				true, dezenasUsuario);

		if (concursosByDezenas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(concursosByDezenas);
	}

	@GetMapping("/populaResultados")
	public ResponseEntity<?> populaResultados() {

		new Thread(() -> {
			try {
				parseContentFileServiceImpl.populaResultados();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<?> buscaUltimoConcurso() {

		ConcursoLotoFacil concurso = concursoService.getUltimoConcurso();
		CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);

		return ResponseEntity.ok().cacheControl(cacheControl).body(concurso);
	}

	@GetMapping("/counter-posicoes")
	public ResponseEntity<?> getCounterPosicao(@RequestParam("page") int page) {

		Map<Long, List<ICounterPosicao>> counterPosicoes = concursoService.getCounterPosicoes(page);
		CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);
		return ResponseEntity.ok().cacheControl(cacheControl).body(counterPosicoes);
	}
}
