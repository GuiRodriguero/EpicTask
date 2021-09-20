package br.com.fiap.epictask.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;

@Controller
public class TaskController {
	
	@Autowired
	private MessageSource message;
	
	@Autowired
	private TaskRepository repository;
	
	//@RequestMapping(value="/task", method=RequestMethod.GET)
	@GetMapping("/task")
	public ModelAndView index() {//Método que mostra a tela das tarefas
		//return "/tasks.html";
		ModelAndView modelAndView = new ModelAndView("tasks");
		List<Task> tasks = repository.findAll();
		//System.out.println(tasks);
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}
	
	@RequestMapping("/task/new")
	public String create(Task task) {//Método que mostra a tela de criação de tarefas
		return "task-form";
	}
	
	//@RequestMapping(value="/task", method=RequestMethod.POST)
	@PostMapping("/task") 
	public String save(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return "task-form";
		}
		repository.save(task);
		redirect.addFlashAttribute("message", message.getMessage("newtask.success", null, LocaleContextHolder.getLocale()));
		return "redirect:tasks";
	}
}
