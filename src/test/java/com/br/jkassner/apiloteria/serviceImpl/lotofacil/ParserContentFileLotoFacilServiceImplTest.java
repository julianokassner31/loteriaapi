package com.br.jkassner.apiloteria.serviceImpl.lotofacil;

import com.br.jkassner.apiloteria.repository.lotofacil.ConcursoLotoFacilRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * @created 07/11/2020 - 14:41
 * @project api-loteria
 * @author Juliano Kassner
 */
@RunWith(MockitoJUnitRunner.class)
public class ParserContentFileLotoFacilServiceImplTest {

    @Spy @InjectMocks
    ParserContentFileLotoFacilConcursoServiceImpl serviceImpl;

    @Mock
    ConcursoLotoFacilRepository lotoFacilRepository;

    @Test
    public void teste_counter_numero_vezes_save_concurso() throws IOException {
        String html = parserHtmlToString();

        doReturn(html).when(serviceImpl).download();

        when(lotoFacilRepository.save(anyObject())).thenReturn(null);

        serviceImpl.populaResultados();

        verify(lotoFacilRepository, times(2075)).save(anyObject());
    }


    private String parserHtmlToString() throws IOException {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("lotofacil/lotofacil.html");
        File file = new File(resource.getURI());
        InputStream inputStream = new FileInputStream(file);

        StringBuilder sb = new StringBuilder();
        int s;
        while (( s = inputStream.read()) > -1) {
            sb.append((char) s);
        }

        return sb.toString();
    }

}
