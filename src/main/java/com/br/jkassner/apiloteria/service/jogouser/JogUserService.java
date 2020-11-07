package com.br.jkassner.apiloteria.service.jogouser;

import com.br.jkassner.apiloteria.exceptions.DezenaInvalidaException;
import com.br.jkassner.apiloteria.model.JogoUser;

import java.util.List;

public interface JogUserService {
    void saveJogoMegaSena(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException;
    void saveJogoLotofacil(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException;
    List<JogoUser> findAllByUser(Long idUser);
}
