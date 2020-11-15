package br.com.jkassner.apiloteria.controller;

import br.com.jkassner.apiloteria.model.CounterPosicao;
import br.com.jkassner.apiloteria.service.AbstractConcursoService;
import br.com.jkassner.apiloteria.model.TipoLoteria;
import br.com.jkassner.apiloteria.service.ParserContentFileService;
import br.com.jkassner.apiloteria.service.counterposicao.CounterPosicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 * @created 08/11/2020 - 02:37
 * @project api-loteria
 * @author Juliano Kassner
 */
public abstract class AbstractController<T> {

    protected AbstractConcursoService<T> concursoService;

    protected ParserContentFileService<T> parserContentFileService;

    @Autowired
    protected CounterPosicaoService counterPosicaoService;

    public AbstractController(AbstractConcursoService<T> concursoService,
                              ParserContentFileService<T> parserContentFileService) {

        this.concursoService = concursoService;
        this.parserContentFileService = parserContentFileService;
    }

    @GetMapping("/{idConcurso}")
    public ResponseEntity<T> getConcurso(@PathVariable("idConcurso") Long idConcurso) {
        T concurso = concursoService.findByIdConcurso(idConcurso);

        return ResponseEntity.ok(concurso);
    }

    @GetMapping("/findConcursos")
    public ResponseEntity<?> findConcursos(@RequestParam(value = "dezenasUsuario") List<Integer> dezenasUsuario) {

        Map<String, List<T>> concursosByDezenas = concursoService.findConcursosByDezenas(dezenasUsuario);

        if (concursosByDezenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(concursosByDezenas);
    }

    @GetMapping
    public ResponseEntity<?> buscaUltimoConcurso() {

        T concurso = concursoService.getUltimoConcurso();
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);

        return ResponseEntity.ok().cacheControl(cacheControl).body(concurso);
    }

    @GetMapping("/counterPosicoes")
    public ResponseEntity<?> getCounterPosicao(@RequestParam("page") int page,
                                               @RequestParam("tpLoteria") TipoLoteria tpLoteria) {

        Map<Long, List<CounterPosicao>> counterPosicoes = counterPosicaoService.getCounterPosicoes(tpLoteria, page);
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);
        return ResponseEntity.ok().cacheControl(cacheControl).body(counterPosicoes);
    }

    @GetMapping("/populaResultados")
    public ResponseEntity<?> populaResultados() {

        new Thread(() -> {
            parserContentFileService.populaResultados();
        }).start();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allConcursos")
    public ResponseEntity<?> allConcursos(@RequestParam("page") int page,
                                         @RequestParam("tpLoteria") TipoLoteria tpLoteria) {

        List<T> concursos = concursoService.allConcursos(page, tpLoteria);

        return ResponseEntity.ok(concursos);
    }
}
