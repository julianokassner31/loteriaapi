package com.br.jkassner.apiloteria.controller.megasena;

import com.br.jkassner.apiloteria.controller.AbstractController;
import com.br.jkassner.apiloteria.model.ConcursoMegaSena;
import com.br.jkassner.apiloteria.service.ParserContentFileService;
import com.br.jkassner.apiloteria.service.concurso.ConcursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/megasena")
public class ConcursoMegaSenaController extends AbstractController<ConcursoMegaSena> {

	@Autowired
	public ConcursoMegaSenaController(@Qualifier("concursoMegaSenaServiceImpl")
												  ConcursoService<ConcursoMegaSena> concursoService,
									  @Qualifier("parseContentFileMegaSenaServiceImpl")
											  ParserContentFileService<ConcursoMegaSena> parserContentFileService) {

		super(concursoService, parserContentFileService);
	}
}
