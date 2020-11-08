package com.br.jkassner.apiloteria.model;

import lombok.Getter;

@Getter
public enum TipoLoteria {

    MEGASENA(
            "concurso_mega_sena",
            new String[]{"pr_dezena","se_dezena", "te_dezena", "qa_dezena", "qi_dezena", "sx_dezena"}
    ),
    LOTOFACIL(
            "concurso_lotofacil",
            new String[]{"pr_dezena","se_dezena", "te_dezena", "qa_dezena", "qi_dezena", "sx_dezena",
                "st_dezena", "ot_dezena", "no_dezena", "dc_dezena", "dpr_dezena", "dse_dezena",
                "dte_dezena", "dqa_dezena", "dqi_dezena"}
    ),
    LOTECA(
            "",
            new String[]{""}
    );

    private String tabela;
    private String[] colunas;

    TipoLoteria(String tabela, String[] colunas) {
        this.tabela = tabela;
        this.colunas = colunas;
    }
}
