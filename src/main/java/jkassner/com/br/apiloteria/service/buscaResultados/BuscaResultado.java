package jkassner.com.br.apiloteria.service.buscaResultados;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BuscaResultado {

    void populaResultados() throws IOException;
    void baixaResultados() throws IOException;
    void unzipArquivosBaixados() throws IOException;
    void parserContentFile() throws FileNotFoundException;
}
