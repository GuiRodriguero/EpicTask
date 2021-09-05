package br.com.fiap.epictask.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictask.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

	@Autowired
	private UserRepository repository;
	
	
}
