package com.br.jkassner.apiloteria.serviceImpl.lotofacil;

import com.br.jkassner.apiloteria.model.ConcursoLotoFacil;
import com.br.jkassner.apiloteria.repository.lotofacil.ConcursoLotoFacilRepository;
import com.br.jkassner.apiloteria.service.DownloadService;
import com.br.jkassner.apiloteria.service.ParserContentFileService;
import com.br.jkassner.apiloteria.serviceImpl.AbstractConcursoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConcursoLotoFacilServiceImpl extends AbstractConcursoServiceImpl<ConcursoLotoFacil> {

    @Autowired
    public ConcursoLotoFacilServiceImpl(ConcursoLotoFacilRepository concursoLotoFacilRepository,
                                        @Qualifier("downloadTodosConcursosZipLotoFacil")
                                                DownloadService downloadService,
                                        @Qualifier("parseContentFileLotoFacilServiceImpl")
                                                ParserContentFileService<ConcursoLotoFacil> parseContentFileServiceImpl
    ) {

        super(concursoLotoFacilRepository, downloadService, parseContentFileServiceImpl);
    }
}
