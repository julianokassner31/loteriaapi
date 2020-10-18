package jkassner.com.br.apiloteria.repository;

import jkassner.com.br.apiloteria.model.JogoUser;
import jkassner.com.br.apiloteria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JogoUserRepository extends JpaRepository<JogoUser, Long> {

    List<JogoUser> findAllByUser(User user);
}
