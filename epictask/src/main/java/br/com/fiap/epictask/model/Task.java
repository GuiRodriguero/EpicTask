package br.com.fiap.epictask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity(name = "TB_TASKS")
public class Task {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "O título é obrigatório")
	private String title;
	
	@Size(min=10, message = "Deve ter pelo menos 10 caracteres")
	private String description;
	
	@Min(value=10, message = "A pontuação mínima é 10")
	@Max(value=500, message = "A pontuação máxima é 500")
	private int points; 
}
