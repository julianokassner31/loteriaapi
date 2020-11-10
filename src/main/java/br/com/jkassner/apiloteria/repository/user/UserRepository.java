package br.com.jkassner.apiloteria.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jkassner.apiloteria.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUserName(String userName);
}
