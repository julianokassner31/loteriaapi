package br.com.jkassner.apiloteria.service.jogouser;

import br.com.jkassner.apiloteria.exceptions.DezenaInvalidaException;
import br.com.jkassner.apiloteria.model.JogoUser;

import java.util.List;

public interface JogUserService {
    void saveJogoMegaSena(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException;
    void saveJogoLotofacil(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException;
    List<JogoUser> findAllByUser(Long idUser);
}
