package jkassner.com.br.apiloteria.service.jogouser;

import jkassner.com.br.apiloteria.exceptions.DezenaInvalidaException;
import jkassner.com.br.apiloteria.model.JogoUser;
import jkassner.com.br.apiloteria.model.User;

import java.util.List;

public interface JogUserService {
    void saveJogoMegaSena(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException;
    void saveJogoLotofacil(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException;
    List<JogoUser> findAllByUser(Long idUser);
}
