package br.com.fiap.epictask.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService service;

	//@RequestMapping(value="/task", method=RequestMethod.GET)	
	@GetMapping
	public ModelAndView index() {//Método que mostra a tela das tarefas
		return service.index();
	}
	
	@RequestMapping("new")
	public String create(Task task) {
		return service.create();
	}
	
	//@RequestMapping(value="/task", method=RequestMethod.POST)
	@PostMapping
	public String save(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
		return service.save(task, result, redirect);
	}
	
	@GetMapping("/hold/{id}")
	public String hold(@PathVariable Long id, Authentication auth) {
		return service.hold(id, auth);
	}
	
	@GetMapping("/release/{id}")
	public String release(@PathVariable Long id, Authentication auth) {
		return service.release(id, auth);
	}

}
