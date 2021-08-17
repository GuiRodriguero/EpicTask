package br.com.fiap.epictask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;

@Controller
public class TaskController {

	@Autowired
	private TaskRepository repository;
	
	//@RequestMapping(value="/task", method=RequestMethod.GET)
	@GetMapping("/task")
	public String index() {//Método que mostra a tela das tarefas
		//return "/tasks.html";
		return "/tasks";
	}
	
	@RequestMapping("/task/new")
	public String create() {//Método que mostra a tela de criação de tarefas
		return "/task-form";
	}
	
	//@RequestMapping(value="/task", method=RequestMethod.POST)
	@PostMapping("/task")
	public String save(Task task) {
		repository.save(task);
		repository.findByTitle(task.getTitle());
		System.out.println("Salvando tarefa");
		return "tasks";
	}
}
