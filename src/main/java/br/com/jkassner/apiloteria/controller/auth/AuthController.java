package br.com.jkassner.apiloteria.controller.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jkassner.apiloteria.model.User;
import br.com.jkassner.apiloteria.repository.user.UserRepository;
import br.com.jkassner.apiloteria.security.AccountCredentialsDto;
import br.com.jkassner.apiloteria.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtotkeJwtTokenProvider;

	@Autowired
	UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity signin(@RequestBody AccountCredentialsDto account) {
		
		try {
			
			String userName = account.getUserName();
			String password = account.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			
			User user = userRepository.findByUserName(userName);
			
			String token = "";
			
			if(user != null) {
				token = jwtotkeJwtTokenProvider.createToken(userName, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Usuario nao encontrado");
			}
			
			Map< Object, Object> model = new HashMap<>();
			model.put("username", userName);
			model.put("token", token);
			
			return ResponseEntity.ok(model);
		
		}catch (AuthenticationException e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}
	
}
