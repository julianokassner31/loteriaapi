package jkassner.com.br.apiloteria.service.validate;

import jkassner.com.br.apiloteria.exceptions.DezenaInvalidaException;
import jkassner.com.br.apiloteria.model.TipoLoteria;

import java.util.List;

public interface ValidateDezenaService {
    void validate(List<Integer> dezenas, TipoLoteria tipoLoteria) throws DezenaInvalidaException;
}
