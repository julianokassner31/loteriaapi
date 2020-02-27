package jkassner.com.br.apiloteria.service.megasena;

import jkassner.com.br.apiloteria.model.ConcursoMegaSena;
import jkassner.com.br.apiloteria.model.DezenasMegaSenaOrdenadas;
import jkassner.com.br.apiloteria.repository.megasena.ConcursoMegaSenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ConcursoMegaSenaServiceImpl implements ConcursoMegaSenaService {

    @Autowired
    ConcursoMegaSenaRepository concursoMegaSenaRepository;

    @Override
    public ConcursoMegaSena findByIdConcurso(Long id) {
        return concursoMegaSenaRepository.findByIdConcurso(id);
    }

    @Override
    public List<ConcursoMegaSena> findConcursosByDezenas(List<Integer> dezenasUsuario) {

        dezenasUsuario.sort(Comparator.naturalOrder());

        Integer primeira = dezenasUsuario.get(0);
        Integer sexta = dezenasUsuario.get(5);

        List<ConcursoMegaSena> concursosList = concursoMegaSenaRepository.findConcursosByDezenas(primeira, sexta);

        return null;
    }
}
