package br.com.fiap.epictask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.epictask.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	//SELECT ... WHERE title = ?
	//List<Task> findByTitle(String title);

	//SELECT ... WHERE title LIKE ?
	Page<Task> findByTitleLike(String title, Pageable pageable);
	
}
