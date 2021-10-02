package br.com.fiap.epictask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//precisamos do prefixo ROLE
	private String name; //ROLE_ADMIN ou ROLE_USER

	@Override
	public String getAuthority() {
		return this.name;
	}

}
