package br.com.jkassner.apiloteria.serviceImpl.lotofacil;

import br.com.jkassner.apiloteria.model.ConcursoLotoFacil;
import br.com.jkassner.apiloteria.repository.lotofacil.ConcursoLotoFacilRepository;
import br.com.jkassner.apiloteria.serviceImpl.AbstractConcursoServiceImpl;
import br.com.jkassner.apiloteria.service.DownloadService;
import br.com.jkassner.apiloteria.service.ParserContentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConcursoLotoFacilServiceImpl extends AbstractConcursoServiceImpl<ConcursoLotoFacil> {

    @Autowired
    public ConcursoLotoFacilServiceImpl(@Qualifier("concursoLotoFacilRepository")
                                                ConcursoLotoFacilRepository concursoLotoFacilRepository,
                                        @Qualifier("downloadTodosConcursosZipLotoFacil")
                                                DownloadService downloadService,
                                        @Qualifier("parseContentFileLotoFacilServiceImpl")
                                                ParserContentFileService<ConcursoLotoFacil> parseContentFileServiceImpl
    ) {

        super(concursoLotoFacilRepository, downloadService, parseContentFileServiceImpl);
    }
}
