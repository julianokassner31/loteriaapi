package br.com.jkassner.apiloteria.service.validate;

import br.com.jkassner.apiloteria.exceptions.DezenaInvalidaException;
import br.com.jkassner.apiloteria.model.TipoLoteria;

import java.util.List;

public interface ValidateDezenaService {
    void validate(List<Integer> dezenas, TipoLoteria tipoLoteria) throws DezenaInvalidaException;
}
