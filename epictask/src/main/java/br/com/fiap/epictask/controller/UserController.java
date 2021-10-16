package br.com.fiap.epictask.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import br.com.fiap.epictask.service.AuthenticationService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MessageSource messages;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("users");
		List<User> users = repository.findAll();
		modelAndView.addObject("users", users);
		System.out.println(users);
		return modelAndView;
	}
	
	@RequestMapping("new")
	public String create(User user) {
		return "user-form";
	}
	
	@PostMapping
	public String save(@Valid User user, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) return "user-form";
		user.setPassword(
					AuthenticationService
					.getPasswordEncoder()
					.encode(user.getPassword())
		);
		System.out.println(user);
		repository.save(user);
		redirect.addFlashAttribute("message", messages.getMessage("newuser.success", null, LocaleContextHolder.getLocale()));
		return "home";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		repository.deleteById(id);
		return "redirect:/users";
	}
}
