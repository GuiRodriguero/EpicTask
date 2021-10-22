package br.com.fiap.epictask.service;

import java.util.List;
import java.util.Optional;

import br.com.fiap.epictask.exception.NotAllowedException;
import br.com.fiap.epictask.exception.TaskNotFoundException;
import br.com.fiap.epictask.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Service
public class TaskService {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private MessageSource message;

	public ModelAndView index() {
		//return "/tasks.html";
		ModelAndView modelAndView = new ModelAndView("tasks");
		List<Task> tasks = findAll();
		//System.out.println(tasks);
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}

	public String create() {//Método que mostra a tela de criação de tarefas
		return "task-form";
	}

	public String save(Task task, BindingResult result, RedirectAttributes redirect){
		if(result.hasErrors()) {
			return "task-form";
		}

		redirect.addFlashAttribute("message", message.getMessage("newtask.success", null, LocaleContextHolder.getLocale()));
		repository.save(task);
		return "redirect:/task";
	}

	public List<Task> findAll() {
		return repository.findAll();
	}

	public String hold(Long id, Authentication auth) {
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

	public String release(Long id, Authentication auth) {
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
