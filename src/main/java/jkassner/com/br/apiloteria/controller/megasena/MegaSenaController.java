package jkassner.com.br.apiloteria.controller.megasena;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.service.buscaResultados.BuscaResultado;
import jkassner.com.br.apiloteria.service.megasena.ConcursoMegaSenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/megasena")
public class MegaSenaController {

    @Autowired
    ConcursoMegaSenaService concursoMegaSenaService;

    @Autowired
    @Qualifier("buscaResultadoMegaSenaService")
    BuscaResultado buscaResultado;

    @GetMapping("/{idConcurso}")
    public ResponseEntity<?> getConcurso(@PathVariable("idConcurso") Long idConcurso) {
        ConcursoMegaSena concursoMegaSena = concursoMegaSenaService.findByIdConcurso(idConcurso);

        return ResponseEntity.ok(concursoMegaSena);
    }

    @PostMapping("/find-concursos")
    public ResponseEntity<?> findConcursos(List<Integer> dezenasUsuario) {
        List<ConcursoMegaSena> concursosByDezenas = concursoMegaSenaService.findConcursosByDezenas(dezenasUsuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/populaResultados")
    public ResponseEntity<?> populaResultados() throws IOException {
        buscaResultado.populaResultados();

        return ResponseEntity.noContent().build();
    }

}
