package br.com.jkassner.apiloteria.serviceImpl.lotofacil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.jkassner.apiloteria.model.ConcursoLotoFacil;
import br.com.jkassner.apiloteria.repository.lotofacil.ConcursoLotoFacilRepository;
import br.com.jkassner.apiloteria.serviceImpl.AbstractConcursoServiceImpl;

@Service
public class ConcursoLotoFacilServiceImpl extends AbstractConcursoServiceImpl<ConcursoLotoFacil> {

    @Autowired
    public ConcursoLotoFacilServiceImpl(@Qualifier("concursoLotoFacilRepository")
                                                ConcursoLotoFacilRepository concursoLotoFacilRepository
    ) {

        super(concursoLotoFacilRepository, "/popula/lotofacil");
    }
}
