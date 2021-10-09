package br.com.fiap.epictask.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.exception.NotAllowedException;
import br.com.fiap.epictask.exception.TaskNotFoundException;
import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.TaskRepository;
import br.com.fiap.epictask.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private MessageSource message;
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private TaskService service;

	//@RequestMapping(value="/task", method=RequestMethod.GET)	
	@GetMapping
	public ModelAndView index() {//Método que mostra a tela das tarefas
		//return "/tasks.html";	
		ModelAndView modelAndView = new ModelAndView("tasks");
		List<Task> tasks = service.findAll();
		//System.out.println(tasks);
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}
	
	@RequestMapping("new")
	public String create(Task task) {
		return service.create();
	}
	
	//@RequestMapping(value="/task", method=RequestMethod.POST)
	@PostMapping
	public String save(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return "task-form";
		}
		repository.save(task);
		redirect.addFlashAttribute("message", message.getMessage("newtask.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/task";
	}
	
	@GetMapping("/hold/{id}")
	public String hold(@PathVariable Long id, Authentication auth) {
		Optional<Task> optional = repository.findById(id);
		
		if(optional.isEmpty()){
			throw new TaskNotFoundException("Tarefa não encontrada");
		}

		Task task = optional.get();
		
		if(task.getUser() != null){
			throw new NotAllowedException("Tarefa já atribuída");
		}
		
		User user = (User) auth.getPrincipal();
		task.setUser(user);
		repository.save(task);
		return "redirect:/task";
	
	}
	
	@GetMapping("/release/{id}")
	public String release(@PathVariable Long id, Authentication auth) {
		Optional<Task> optional = repository.findById(id);
		
		if(optional.isEmpty()){
			throw new TaskNotFoundException("Tarefa não encontrada");
		}

		Task task = optional.get();
		User user = (User) auth.getPrincipal();
		
		if(!task.getUser().equals(user)){//usuário da tarefa != usuário logado
			throw new NotAllowedException("Essa tarefa não está atribuída para você");
		}
		
		task.setUser(null);
		repository.save(task);
		return "redirect:/task";
	
	}
}
