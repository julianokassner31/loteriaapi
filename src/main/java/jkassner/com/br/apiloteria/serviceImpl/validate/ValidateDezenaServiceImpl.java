package jkassner.com.br.apiloteria.serviceImpl.validate;

import jkassner.com.br.apiloteria.exceptions.DezenaInvalidaException;
import jkassner.com.br.apiloteria.model.TipoLoteria;
import jkassner.com.br.apiloteria.service.validate.ValidateDezenaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

/*
 * @created 17/10/2020 - 23:04
 * @project api-loteria
 * @author Juliano Kassner
 */
@Service
public class ValidateDezenaServiceImpl implements ValidateDezenaService {

    @Override
    public void validate(List<Integer> dezenas, TipoLoteria tipoLoteria) throws DezenaInvalidaException {

        Predicate<Long> predicateQtDezenasEscolhidas;
        Predicate<Integer> predicateDezenaMaior;
        Predicate<Integer> predicateDezenaMenor1 = (dezena) -> dezena < 1;

        switch (tipoLoteria) {
            case MEGASENA:
                predicateQtDezenasEscolhidas = (lenght) -> lenght >= 6 && lenght <=15;
                predicateDezenaMaior = (dezena) -> dezena > 60;
                break;

            case LOTOFACIL:
                predicateQtDezenasEscolhidas = (lenght) -> lenght >= 15 && lenght <= 18;
                predicateDezenaMaior = (dezena) -> dezena > 25;
                break;

            case LOTECA:
                predicateQtDezenasEscolhidas = (lenght) -> lenght >= 15 && lenght <= 25;
                predicateDezenaMaior = (dezena) -> dezena > 15;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + tipoLoteria);
        }

        if (!predicateQtDezenasEscolhidas.test(dezenas.stream().distinct().count()))
            throw new DezenaInvalidaException("O número de dezenas é menor/maior que o tipo aceito pela loteria escolhida!");

        if (dezenas.stream().anyMatch(predicateDezenaMenor1.or(predicateDezenaMaior)))
            throw new DezenaInvalidaException("Existem dezenas inválidas para o tipo de loteria escolhido!");
    }
}
