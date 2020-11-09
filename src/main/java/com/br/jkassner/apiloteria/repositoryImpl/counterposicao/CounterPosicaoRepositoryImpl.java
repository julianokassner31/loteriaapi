package com.br.jkassner.apiloteria.repositoryImpl.counterposicao;

import br.com.jkassner.apiloteria.counterposicao.sql.CounterPosicaoSql;
import com.br.jkassner.apiloteria.model.CounterPosicao;
import com.br.jkassner.apiloteria.model.TipoLoteria;
import com.br.jkassner.apiloteria.repositoryImpl.abstractjdbctemplate.AbstractJdbcTemplateRepositoryImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class CounterPosicaoRepositoryImpl extends AbstractJdbcTemplateRepositoryImpl {

    public Map<Long, List<CounterPosicao>> getCounterPosicoes(TipoLoteria tipoLoteria, int page) {

        Map<Long, List<CounterPosicao>> map = new HashMap<>();

        for (String col : tipoLoteria.getColunas()) {
            String query = String.format(CounterPosicaoSql.counter_posicao_dezena, col, col, tipoLoteria.getTabela(), col, col, page);
            List<CounterPosicao> posicoes = getJdbcTemplate().query(query, new BeanPropertyRowMapper(CounterPosicao.class));

            if (map.isEmpty()) {
                posicoes.forEach(cp -> {
                    LinkedList<CounterPosicao> counterPosicoes = new LinkedList<>();
                    counterPosicoes.add(cp);
                    map.put(cp.getDezena(), counterPosicoes);
                });
            } else {
                posicoes.forEach(cp -> {
                    map.get(cp.getDezena()).add(cp);
                });
            }
        }

        return map;
    }
}
