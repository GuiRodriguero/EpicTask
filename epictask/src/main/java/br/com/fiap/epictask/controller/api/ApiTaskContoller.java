package br.com.fiap.epictask.controller.api;

import java.util.Optional;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;

@RestController
@RequestMapping("/api/task")
public class ApiTaskContoller {

	@Autowired
	private TaskRepository repository;
	
	//@GetMapping("/api/task")
	//@ResponseBody //Avisa ao Spring que isso é um corpo de resposta, e não uma view igual nos outros controllers
	@GetMapping
	public Page<Task> index(@RequestParam(required = false) String title, @PageableDefault(size = 5) Pageable pageable) {
		if(title == null){
			return repository.findAll(pageable);
				
		}
		return repository.findByTitleLike("%" + title + "%", pageable);
	}
	
	//@PostMapping("/api/task")
	//@ResponseBody Não usamos mais pois mudamos de Controller para RestController
	@PostMapping
	public ResponseEntity<Task> create(@RequestBody Task task, UriComponentsBuilder uriBuilder) {
		//RequestBody - corpo da requisição
		//ResponseEntity - Permite que controlamos qual o código de resposta
		
		repository.save(task);
		
		URI uri = uriBuilder.path("/api/task/1").buildAndExpand(task.getId()).toUri();
		
		return ResponseEntity.created(uri).body(task);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Task> show(@PathVariable Long id){
		return ResponseEntity.of(repository.findById(id));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Task> destroy(@PathVariable Long id){
		Optional<Task> task = repository.findById(id);
		
		if(task.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.ok().build(); //.build() porque não tem corpo de resposta
	}
}
