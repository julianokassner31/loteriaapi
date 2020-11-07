package com.br.jkassner.apiloteria.service.validate;

import com.br.jkassner.apiloteria.exceptions.DezenaInvalidaException;
import com.br.jkassner.apiloteria.model.TipoLoteria;

import java.util.List;

public interface ValidateDezenaService {
    void validate(List<Integer> dezenas, TipoLoteria tipoLoteria) throws DezenaInvalidaException;
}
