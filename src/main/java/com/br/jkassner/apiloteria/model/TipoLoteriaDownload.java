package com.br.jkassner.apiloteria.model;

public enum TipoLoteriaDownload {
    
	MEGASENA_TODOS("D_megase.zip", 
    		"d_mega.htm", 
    		"http://www1.caixa.gov.br/loterias/_arquivos/loterias/D_megase.zip"),
    
    MEGASENA_ULTIMO_CONCURSO("", 
    		"ultimo_concurso.html", 
    		"http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/"),
    
    MEGASENA_NR_CONCURSO("", 
    		"nr_concurso.html", 
    		""),
    
    LOTOFACIL_TODOS("D_lotfac.zip", 
    		"d_lotfac.htm", 
    		"http://www1.caixa.gov.br/loterias/_arquivos/loterias/D_lotfac.zip"),
    
    QUINA_TODOS("D_quina.zip", 
    		"d_quina.htm", ""),
    
    LOTOMANIA_TODOS("D_lotoma.zip", 
    		"d_lotoma.htm", 
    		"");

    private String arquivoZip;
    private String arquivoHtm;
    private String uri;

    TipoLoteriaDownload(String arquivoZip, String arquivoHtm, String uri) {
        this.arquivoZip = arquivoZip;
        this.arquivoHtm = arquivoHtm;
        this.uri = uri;
    }

    public String getArquivoZip(){
        return this.arquivoZip;
    }

    public String getArquivoHtm(){
        return this.arquivoHtm;
    }
    
    public String getUri() {
    	return this.uri;
    }
}
