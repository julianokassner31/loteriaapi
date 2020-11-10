package br.com.jkassner.apiloteria.serviceImpl.jogouser;

import br.com.jkassner.apiloteria.exceptions.DezenaInvalidaException;
import br.com.jkassner.apiloteria.model.DezenaUser;
import br.com.jkassner.apiloteria.model.TipoLoteria;
import br.com.jkassner.apiloteria.repository.jogouser.JogoUserRepository;
import br.com.jkassner.apiloteria.repository.user.UserRepository;
import br.com.jkassner.apiloteria.service.jogouser.JogUserService;
import br.com.jkassner.apiloteria.service.validate.ValidateDezenaService;
import br.com.jkassner.apiloteria.model.JogoUser;
import br.com.jkassner.apiloteria.model.User;
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
