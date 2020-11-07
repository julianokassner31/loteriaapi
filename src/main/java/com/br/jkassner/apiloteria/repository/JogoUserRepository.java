package com.br.jkassner.apiloteria.repository;

import com.br.jkassner.apiloteria.model.JogoUser;
import com.br.jkassner.apiloteria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JogoUserRepository extends JpaRepository<JogoUser, Long> {

    List<JogoUser> findAllByUser(User user);
}
