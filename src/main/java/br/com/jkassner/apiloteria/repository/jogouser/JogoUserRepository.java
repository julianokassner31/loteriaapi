package br.com.jkassner.apiloteria.repository.jogouser;

import br.com.jkassner.apiloteria.model.JogoUser;
import br.com.jkassner.apiloteria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoUserRepository extends JpaRepository<JogoUser, Long> {
    List<JogoUser> findAllByUser(User user);
}
