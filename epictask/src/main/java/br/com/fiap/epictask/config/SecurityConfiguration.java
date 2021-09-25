package br.com.fiap.epictask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.fiap.epictask.service.AuthenticationService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AuthenticationService authenticationService;

	//Autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService)
			.passwordEncoder(AuthenticationService.getPasswordEncoder());
	}
	
	//Autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().anyRequest().permitAll(); //Permite tudo e todos
		
		//Requisições que são "/user" e que começam com "/task" precisam de autenticação
		http.authorizeRequests()
		.antMatchers("/user", "/task/**")
			.authenticated()
		.anyRequest()
			.permitAll()
		.and()
			.formLogin();
//		.and()
//			.csrf()
//			.disable(); 
		
	}
}
