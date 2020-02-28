package jkassner.com.br.apiloteria.sql

class MegaSenaSql {

    public static final String FIND_CONCURSOS_BY_ID_DEZENA = """
        SELECT
            sena.*
        FROM
            mega_sena AS sena
                INNER JOIN dezenas_mega_sena_ordenadas AS do1 ON do1.id = sena.id_dezenas_ordenadas
        WHERE
                do1.primeira = :primeira AND do1.sexta = :ultima
                
        UNION
        
        SELECT
            quina.*
        FROM
            mega_sena as quina
                inner join dezenas_mega_sena_ordenadas AS do2 ON do2.id = quina.id_dezenas_ordenadas
        WHERE
                do2.primeira = :primeira AND do2.quinta = :ultima
                
        UNION
        
        SELECT
            quina.*
        FROM
            mega_sena as quina
                inner join dezenas_mega_sena_ordenadas AS do2 ON do2.id = quina.id_dezenas_ordenadas
        WHERE
                do2.primeira = :primeira AND do2.quarta = :ultima
                
        UNION
        
        SELECT
            quina2.*
        FROM
            mega_sena as quina2
                inner join dezenas_mega_sena_ordenadas AS do2 ON do2.id = quina2.id_dezenas_ordenadas
        WHERE
                do2.segunda = :primeira AND do2.sexta = :ultima
        
        UNION
        
        SELECT
            quadra.*
        FROM
            mega_sena as quadra
                INNER JOIN dezenas_mega_sena_ordenadas AS do3 ON do3.id = quadra.id_dezenas_ordenadas
        WHERE
                do3.primeira = :primeira AND do3.quarta = :ultima
        
        UNION
        
        SELECT
            quadra2.*
        FROM
            mega_sena as quadra2
                INNER JOIN dezenas_mega_sena_ordenadas AS do3 ON do3.id = quadra2.id_dezenas_ordenadas
        WHERE
                do3.segunda = :primeira AND do3.quinta = :ultima
        
        UNION
        
        SELECT
            quadra3.*
        FROM
            mega_sena as quadra3
                INNER JOIN dezenas_mega_sena_ordenadas AS do3 ON do3.id = quadra3.id_dezenas_ordenadas
        WHERE
                do3.terceira = :primeira AND do3.sexta = :ultima
    """
}
