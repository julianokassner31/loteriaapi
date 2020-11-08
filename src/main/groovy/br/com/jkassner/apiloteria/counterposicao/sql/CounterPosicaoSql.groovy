package br.com.jkassner.apiloteria.counterposicao.sql

class CounterPosicaoSql {

    public static String counter_posicao_dezena = """
        
        SELECT
            COUNT(d.%s) AS count, 
            d.%s as dezena
        FROM 
            %s d 
        GROUP BY 
            (d.%s) 
        ORDER BY 
            d.%s
        LIMIT 10
        OFFSET %s
    """
}
