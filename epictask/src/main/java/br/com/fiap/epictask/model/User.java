package br.com.fiap.epictask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity(name = "TB_USER")
public class User {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório")
	private String name;
	
	@NotBlank(message = "O email é obrigatório")
	private String email;
	
	@Size(min=6, message = "Deve ter pelo menos 6 caracteres")
	private String password;
	
}