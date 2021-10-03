package br.com.fiap.epictask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository repository;
	
	public String create() {//Método que mostra a tela de criação de tarefas
		return "task-form";
	}

	public List<Task> findAll() {
		return repository.findAll();
	}

}
