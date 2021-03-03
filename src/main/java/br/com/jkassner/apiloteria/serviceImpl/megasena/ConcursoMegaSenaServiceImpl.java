package br.com.jkassner.apiloteria.serviceImpl.megasena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jkassner.apiloteria.model.ConcursoMegaSena;
import br.com.jkassner.apiloteria.repository.megasena.ConcursoMegaSenaRepository;
import br.com.jkassner.apiloteria.serviceImpl.AbstractConcursoServiceImpl;

@Service("concursoMegaSenaServiceImpl")
public class ConcursoMegaSenaServiceImpl extends AbstractConcursoServiceImpl<ConcursoMegaSena> {

	ConcursoMegaSenaRepository repository;

    @Autowired
    public ConcursoMegaSenaServiceImpl(ConcursoMegaSenaRepository repository) {

    	super(repository, "/popula/megasena");

    	this.repository = repository;
    }

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
            senas = repository.findSenas(primeira, segunda, terceira, quarta, quinta, sexta);

            if (!senas.isEmpty()) {
                concursosMap.put("senas", senas);
            }
        }

        if (senas.isEmpty()) {

            List<ConcursoMegaSena> possiveisQuinasQuadras = repository.findPossiveisQuinasEQuadras(primeira,
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
}
