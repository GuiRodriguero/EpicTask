package br.com.fiap.epictask.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;

@Controller
public class TaskController {

	@Autowired
	private TaskRepository repository;
	
	//@RequestMapping(value="/task", method=RequestMethod.GET)
	@GetMapping("/task")
	public ModelAndView index() {//Método que mostra a tela das tarefas
		//return "/tasks.html";
		ModelAndView modelAndView = new ModelAndView("tasks");
		List<Task> tasks = repository.findAll();
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}
	
	@RequestMapping("/task/new")
	public String create() {//Método que mostra a tela de criação de tarefas
		return "task-form";
	}
	
	//@RequestMapping(value="/task", method=RequestMethod.POST)
	@PostMapping("/task")
	public String save(@Valid Task task, BindingResult result) {
		if(result.hasErrors()) {
			return "tasks-form";
		}
		repository.save(task);
		return "tasks";
	}
}
