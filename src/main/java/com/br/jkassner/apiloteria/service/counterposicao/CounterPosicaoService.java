package com.br.jkassner.apiloteria.service.counterposicao;

import com.br.jkassner.apiloteria.model.CounterPosicao;
import com.br.jkassner.apiloteria.model.TipoLoteria;
import com.br.jkassner.apiloteria.repositoryImpl.counterposicao.CounterPosicaoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CounterPosicaoService {

	@Autowired
	CounterPosicaoRepositoryImpl counterPosicaoRepository;

	public Map<Long, List<CounterPosicao>> getCounterPosicoes(TipoLoteria tipoLoteria, int page) {

		return counterPosicaoRepository.getCounterPosicoes(tipoLoteria, page);
	}
}
