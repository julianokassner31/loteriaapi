package jkassner.com.br.apiloteria.service.buscaResultados;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public abstract class BuscaResultadoAbstract implements BuscaResultado {

    protected TipoLoteria tipoLoteria;
    private static final String PATH_SAVE = System.getProperty("java.io.tmpdir").concat("/");
    private static final String URI = "http://www1.caixa.gov.br/loterias/_arquivos/loterias/";

    public void baixaResultados() throws IOException {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URI.concat(tipoLoteria.getArquivoZip()));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        InputStream is = entity.getContent();
        String filePath = PATH_SAVE.concat(tipoLoteria.getArquivoZip());
        
        new File(PATH_SAVE.concat(tipoLoteria.getArquivoZip())).delete();
        new File(PATH_SAVE.concat(tipoLoteria.getArquivoHtm())).delete();
        
        FileOutputStream fos = new FileOutputStream(new File(filePath));

        int inByte;
        while ((inByte = is.read()) != -1) {
            fos.write(inByte);
        }
        is.close();
        fos.close();
        client.close();
        System.out.println("File Download Completed!!!");
    }

    public void unzipArquivosBaixados() throws IOException {
        String filePath = PATH_SAVE.concat(tipoLoteria.getArquivoZip());
        FileOutputStream fos;
        ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath));
        ZipEntry zipEntry = zis.getNextEntry();
        File destDir = new File(PATH_SAVE);
        byte[] buffer = new byte[1024];
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    protected int converterToInt(int pos, Elements td) {
        Element prDezena = td.get(pos);
        return Integer.parseInt(prDezena.text());
    }

    protected BigDecimal converterToBigDecimal(int pos, Elements td) {
        Element prDezena = td.get(pos);
        String replace = prDezena.text().replace(".", "").replace(",", ".");
        return new BigDecimal(replace);
    }

    protected String getContentFile() throws FileNotFoundException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(PATH_SAVE.concat(tipoLoteria.getArquivoHtm())), StandardCharsets.ISO_8859_1)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    protected File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
