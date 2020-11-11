package br.com.jkassner.apiloteria.sql;

/*
 * @created 11/11/2020 - 19:39
 * @project api-loteria
 * @author Juliano Kassner
 */
public class CounterPosicaoSql {

    public static String counter_posicao_dezena = "SELECT\n" +
            "            COUNT(d.%s) AS count, \n" +
            "            d.%s as dezena\n" +
            "        FROM \n" +
            "            %s d \n" +
            "        GROUP BY \n" +
            "            (d.%s) \n" +
            "        ORDER BY \n" +
            "            d.%s\n" +
            "        LIMIT 10\n" +
            "        OFFSET %s";

}
