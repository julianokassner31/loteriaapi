package jkassner.com.br.apiloteria;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiLoteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLoteriaApplication.class, args);
//        try {
//            CloseableHttpClient client = HttpClientBuilder.create().build();
//            HttpGet request = new HttpGet(
//                    "http://www1.caixa.gov.br/loterias/_arquivos/loterias/D_megase.zip");
//
//            HttpResponse response = client.execute(request);
//            HttpEntity entity = response.getEntity();
//
//            int responseCode = response.getStatusLine().getStatusCode();
//
//            System.out.println("Request Url: " + request.getURI());
//            System.out.println("Response Code: " + responseCode);
//
//            InputStream is = entity.getContent();
//            String filePath = "/home/jkassner/Downloads/D_megase.zip";
//            FileOutputStream fos = new FileOutputStream(new File(filePath));
//
//            int inByte;
//            while ((inByte = is.read()) != -1) {
//                fos.write(inByte);
//            }
//
//            is.close();
//            fos.close();
//
//            client.close();
//            System.out.println("File Download Completed!!!");
//
//            ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath));
//            ZipEntry zipEntry = zis.getNextEntry();
//            File destDir = new File("/home/jkassner/Downloads/");
//            byte[] buffer = new byte[1024];
//            while (zipEntry != null) {
//                File newFile = newFile(destDir, zipEntry);
//                fos = new FileOutputStream(newFile);
//                int len;
//                while ((len = zis.read(buffer)) > 0) {
//                    fos.write(buffer, 0, len);
//                }
//                fos.close();
//                zipEntry = zis.getNextEntry();
//            }
//            zis.closeEntry();
//            zis.close();
//
//            String contentFile = getContentFile();
//
//            Document doc = Jsoup.parse(contentFile);
//            Element body = doc.body();
//            Elements tables = body.getElementsByTag("table");
//            Element table = tables.first();
//            Elements trs = table.getElementsByTag("tr");
//            Iterator<Element> iterator = trs.iterator();
//
//
//            while (iterator.hasNext()) {
//                try {
//                    Element tr = iterator.next();
//                    Elements concurso = tr.getElementsContainingText("Concurso");
//                    if (!concurso.isEmpty()) continue;
//
//                    Elements td = tr.getElementsByTag("td");
//
//                    MegaSena megaSena = new MegaSena();
//
//                    System.out.println(td.get(0));//Concurso
//                    Element nrConcurso = td.get(0);
//
//                    long id = Long.parseLong(nrConcurso.text());
//                    megaSena.setIdConcurso(id);
//
//                    System.out.println(td.get(1));//Data Sorteio
//                    Element dtSorteio = td.get(1);
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                    Date data = simpleDateFormat.parse(dtSorteio.text());
//                    megaSena.setDtSorteio(data);
//
//                    System.out.println(td.get(2));//1ª Dezena
//                    megaSena.setPrDezena(converterToInt(2, td));
//
//                    System.out.println(td.get(3));//2ª Dezena
//                    megaSena.setSeDezena(converterToInt(3, td));
//
//                    System.out.println(td.get(4));//3ª Dezena
//                    megaSena.setTeDezena(converterToInt(4, td));
//
//                    System.out.println(td.get(5));//4ª Dezena
//                    megaSena.setQaDezena(converterToInt(5, td));
//
//                    System.out.println(td.get(6));//5ª Dezena
//                    megaSena.setQiDezena(converterToInt(6, td));
//
//                    System.out.println(td.get(7));//6ª Dezena
//                    megaSena.setSxDezena(converterToInt(7, td));
//
//                    System.out.println(td.get(8));//Arrecadacao_Total
//                    megaSena.setVlArrecadacaoTotal(converterToBigDecimal(8, td));
//
//                    System.out.println(td.get(9));//Ganhadores_Sena
//                    megaSena.setNrGanhadoresSena(converterToInt(9, td));
//
//                    System.out.println(td.get(10));//Cidade
//                    Element elCidade = td.get(10);
//                    megaSena.setNmCidade(elCidade.text());
//
//                    System.out.println(td.get(11));//UF
//                    Element elUf = td.get(11);
//                    megaSena.setNmCidade(elUf.text());
//
//                    System.out.println(td.get(12));//Rateio_Sena
//                    megaSena.setVlRateioSena(converterToBigDecimal(12, td));
//
//                    System.out.println(td.get(13));//Ganhadores_Quina
//                    megaSena.setNrGanhadoresSena(converterToInt(13, td));
//
//                    System.out.println(td.get(14));//Rateio_Quina
//                    megaSena.setVlRateioQuina(converterToBigDecimal(14, td));
//
//                    System.out.println(td.get(15));//Ganhadores_Quadra
//                    megaSena.setNrGanhadoresQuadra(converterToInt(15, td));
//
//                    System.out.println(td.get(16));//Rateio_Quadra
//                    megaSena.setVlRateioQuadra(converterToBigDecimal(16, td));
//
//                    System.out.println(td.get(17));//Acumulado
//                    megaSena.setAcumulado(td.get(17).text().equals("SIM"));
//
//                    System.out.println(td.get(18));//Valor_Acumulado
//                    megaSena.setVlAcumulado(converterToBigDecimal(18, td));
//
//                    System.out.println(td.get(19));//Estimativa_Prêmio
//                    megaSena.setVlEstimativaPremio(converterToBigDecimal(19, td));
//
//                    System.out.println(td.get(20));//Acumulado_Mega_da_Virada
//                    megaSena.setVlAcumuladoMegaVirada(converterToBigDecimal(20, td));
//
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                } catch (Exception e) {
//                    e.printStackTrace();
//					System.out.println();
//					System.out.println();
//                    continue;
//                }
//            }
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (UnsupportedOperationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static int converterToInt(int pos, Elements td) {
//        Element prDezena = td.get(pos);
//        return Integer.parseInt(prDezena.text());
//    }
//
//    public static BigDecimal converterToBigDecimal(int pos, Elements td) {
//        Element prDezena = td.get(pos);
//        String replace = prDezena.text().replace(".", "").replace(",", ".");
//        return new BigDecimal(replace);
//    }
//
//    public static String getContentFile() throws FileNotFoundException {
//        StringBuilder contentBuilder = new StringBuilder();
//        try (Stream<String> stream = Files.lines(Paths.get("/home/jkassner/Downloads/d_mega.htm"), StandardCharsets.ISO_8859_1)) {
//            stream.forEach(s -> contentBuilder.append(s).append("\n"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return contentBuilder.toString();
//    }
//
//    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
//        File destFile = new File(destinationDir, zipEntry.getName());
//
//        String destDirPath = destinationDir.getCanonicalPath();
//        String destFilePath = destFile.getCanonicalPath();
//
//        if (!destFilePath.startsWith(destDirPath + File.separator)) {
//            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
//        }
//
//        return destFile;
//    }
    }
}
