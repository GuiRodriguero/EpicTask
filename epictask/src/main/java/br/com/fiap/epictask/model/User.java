package br.com.fiap.epictask.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity(name = "TB_USER")
@SequenceGenerator(name="user", sequenceName = "SQ_TB_USER", allocationSize = 1)
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "{user.name.blank}")
	private String name;
	
	@NotBlank(message = "{user.email.blank}")
	@Email(message = "{user.email.invalid}")
	private String email;
	
	@Size(min = 8, message = "{user.password.size}")
	private String password;
	
	@NotBlank(message = "{user.github.blank}")
	private String githubuser;
	
	public String getAvatarUrl() {
		return "https://avatars.githubusercontent.com/" + this.githubuser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
