package br.com.fiap.epictask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity(name = "TB_TASKS")
@SequenceGenerator(name="task", sequenceName = "SQ_TB_TASK", allocationSize = 1)
public class Task {

	@Id @GeneratedValue(generator = "task", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "{task.title.blank}")
	private String title;
	
	@Size(min=10, message = "{task.description.size}")
	private String description;
	
	@Min(value=10, message = "{task.points.min}")
	@Max(value=500, message = "{task.points.max}")
	private int points; 
	
	@Min(value=0, message = "{task.status.min}")
	@Max(value=10, message = "{task.status.max}")
	private int status;
	
	@ManyToOne
	private User user;
}
