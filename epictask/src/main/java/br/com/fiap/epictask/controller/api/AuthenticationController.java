package br.com.fiap.epictask.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictask.model.Login;
import br.com.fiap.epictask.service.TokenService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/api/auth")
	public ResponseEntity<Object> auth(@RequestBody @Valid Login login){
		Authentication auth = login.getAuthentication();
		try {
			
			Authentication authenticate = authManager.authenticate(auth);		

			String token = tokenService.createToken(authenticate);
			
			return ResponseEntity.ok(token);
			
		}catch(AuthenticationException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
}
