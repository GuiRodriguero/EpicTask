package br.com.fiap.epictask.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;


@Controller
public class UserController {
	
	@Autowired
	private UserRepository repository;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/login/new")
	public String create(User user) {
		return "login-form";
	}
	
	@PostMapping("/login") 
	public String save(@Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			return "login-form";
		}
		repository.save(user);
		return "login";
	}
}
