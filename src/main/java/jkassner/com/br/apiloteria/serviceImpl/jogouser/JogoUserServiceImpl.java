package jkassner.com.br.apiloteria.serviceImpl.jogouser;

import jkassner.com.br.apiloteria.exceptions.DezenaInvalidaException;
import jkassner.com.br.apiloteria.model.DezenaUser;
import jkassner.com.br.apiloteria.model.JogoUser;
import jkassner.com.br.apiloteria.model.TipoLoteria;
import jkassner.com.br.apiloteria.model.User;
import jkassner.com.br.apiloteria.repository.JogoUserRepository;
import jkassner.com.br.apiloteria.repository.UserRepository;
import jkassner.com.br.apiloteria.service.jogouser.JogUserService;
import jkassner.com.br.apiloteria.service.validate.ValidateDezenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @created 17/10/2020 - 22:55
 * @project api-loteria
 * @author Juliano Kassner
 */
@Service
public class JogoUserServiceImpl implements JogUserService {

    @Autowired
    ValidateDezenaService validateDezenaService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JogoUserRepository jogoUserRepository;

    @Override
    public void saveJogoMegaSena(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException {
        validateDezenaService.validate(dezenas, TipoLoteria.MEGASENA);
        saveJogo(idUser, dezenas, TipoLoteria.MEGASENA);
    }

    @Override
    public void saveJogoLotofacil(Long idUser, List<Integer> dezenas) throws DezenaInvalidaException {
        validateDezenaService.validate(dezenas, TipoLoteria.LOTOFACIL);
    }

    @Override
    public List<JogoUser> findAllByUser(Long idUser) {
        User user = userRepository.getOne(idUser);

        return jogoUserRepository.findAllByUser(user);
    }

    public void saveJogo(Long idUser, List<Integer> dezenas, TipoLoteria tipoLoteria) {
        User user = userRepository.getOne(idUser);

        JogoUser jogoUser = new JogoUser();
        jogoUser.setUser(user);
        jogoUser.setTipoLoteria(tipoLoteria);

        for (Integer dezena : dezenas) {
            DezenaUser dezenaUser = new DezenaUser();
            dezenaUser.setNrDezena(dezena);
            dezenaUser.setJogoUser(jogoUser);
            jogoUser.getDezenaUsers().add(dezenaUser);
        }

        jogoUserRepository.save(jogoUser);
    }
}
