package jkassner.com.br.apiloteria.controller.jogouser;

import jkassner.com.br.apiloteria.exceptions.DezenaInvalidaException;
import jkassner.com.br.apiloteria.model.JogoUser;
import jkassner.com.br.apiloteria.service.jogouser.JogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @created 17/10/2020 - 22:38
 * @project api-loteria
 * @author Juliano Kassner
 */
@RestController
@RequestMapping("/jogo/{idUser}")
public class JogoUserController {

    @Autowired
    JogUserService jogoUserService;

    @PostMapping("megasena")
    public ResponseEntity<?> cadastrarJogoMegaSena(@PathVariable("idUser") Long idUser, @RequestBody List<Integer> dezenas) throws DezenaInvalidaException {

        jogoUserService.saveJogoMegaSena(idUser, dezenas);

        return ResponseEntity.ok().build();
    }

    @GetMapping("megasena")
    public ResponseEntity<?> getJogosMegaSena(@PathVariable("idUser") Long idUser) {

        List<JogoUser> jogos = jogoUserService.findAllByUser(idUser);

        return ResponseEntity.ok(jogos);
    }

    @PostMapping("lotofacil")
    public ResponseEntity<?> cadastrarJogoLotofacil(@PathVariable("idUser") Long idUser, @RequestBody List<Integer> dezenas) throws DezenaInvalidaException {

        jogoUserService.saveJogoLotofacil(idUser, dezenas);

        return ResponseEntity.ok().build();
    }
}
