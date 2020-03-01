package jkassner.com.br.apiloteria.service.megasena;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.repository.megasena.ConcursoMegaSenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConcursoMegaSenaServiceImpl implements ConcursoMegaSenaService {

    @Autowired
    ConcursoMegaSenaRepository concursoMegaSenaRepository;

    @Override
    public ConcursoMegaSena findByIdConcurso(Long idConcurso) {
        return concursoMegaSenaRepository.findByIdConcurso(idConcurso);
    }

    @Override
    public Map<String, List<ConcursoMegaSena>> findConcursosByDezenas(boolean findSena, boolean findQUina, boolean findQuadra, List<Integer> dezenasUsuario) {

        dezenasUsuario.sort(Comparator.naturalOrder());
        Integer primeira = dezenasUsuario.get(0);
        Integer segunda = dezenasUsuario.get(1);
        Integer terceira = dezenasUsuario.get(2);
        Integer quarta = dezenasUsuario.get(3);
        Integer quinta = dezenasUsuario.get(4);
        Integer sexta    = dezenasUsuario.get(5);

        Map<String, List<ConcursoMegaSena>> concursosMap = new HashMap<>();
        
        List<ConcursoMegaSena> senas = new ArrayList<ConcursoMegaSena>();
        
        if (findSena) {
        	senas = concursoMegaSenaRepository.findSenas(primeira, segunda, terceira, quarta, quinta, sexta);
            concursosMap.put("senas", senas);
        }

        
        if(senas.isEmpty()) {
        	
        	if (findQUina) {
                concursosMap.put("quinas", concursoMegaSenaRepository.findQuinas(primeira, segunda, terceira, quarta, quinta, sexta));
            }

//            if (findQuadra) {
//                concursosMap.put("quadras", concursoMegaSenaRepository.findQuadras(primeira, segunda, terceira, quarta, quinta, sexta));
//            }
        }
        

        return concursosMap;
    }
}
