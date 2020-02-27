package jkassner.com.br.apiloteria.service.buscaResultados;

public enum TipoLoteria {
    MEGASENA("D_megase.zip", "d_mega.htm"),
    LOTOFACIL("D_lotfac.zip", "d_lotfac.htm"),
    QUINA("D_quina.zip", "d_quina.htm"),
    LOTOMANIA("D_lotoma.zip", "d_lotoma.htm");

    private String arquivoZip;
    private String arquivoHtm;

    TipoLoteria(String arquivoZip, String arquivoHtm) {
        this.arquivoZip = arquivoZip;
        this.arquivoHtm = arquivoHtm;
    }

    public String getArquivoZip(){
        return this.arquivoZip;
    }

    public String getArquivoHtm(){
        return this.arquivoHtm;
    }
}
