package com.br.jkassner.apiloteria.controller.megasena;

import com.br.jkassner.apiloteria.controller.AbstractController;
import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import com.br.jkassner.apiloteria.service.ParserContentFileService;
import com.br.jkassner.apiloteria.serviceImpl.megasena.ConcursoMegaSenaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/megasena")
public class ConcursoMegaSenaController extends AbstractController<ConcursoMegaSena> {

	private ConcursoMegaSenaServiceImpl concursoMegaSenaService;

	@Autowired
	public ConcursoMegaSenaController(@Qualifier("concursoMegaSenaServiceImpl")
												  ConcursoMegaSenaServiceImpl concursoMegaSenaService,
									  @Qualifier("parseContentFileMegaSenaServiceImpl")
											  ParserContentFileService<ConcursoMegaSena> parserContentFileService) {

		super(concursoMegaSenaService, parserContentFileService);

		this.concursoMegaSenaService = concursoMegaSenaService;
	}

	@GetMapping("findConcursosByDezenas")
	public ResponseEntity<?> findConcursosByDezenas(@RequestParam("dezenas")List<Integer> dezenas) {
		Map<String, List<ConcursoMegaSena>> concursosByDezenas =
				concursoMegaSenaService.findConcursosByDezenas(true, true, true, dezenas);

		return ResponseEntity.ok(concursosByDezenas);
	}
}
