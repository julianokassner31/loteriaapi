package br.com.jkassner.apiloteria.service.counterposicao;

import br.com.jkassner.apiloteria.model.CounterPosicao;
import br.com.jkassner.apiloteria.model.TipoLoteria;
import br.com.jkassner.apiloteria.repositoryImpl.counterposicao.CounterPosicaoRepositoryImpl;
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
