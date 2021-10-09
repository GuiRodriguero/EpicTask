package br.com.fiap.epictask.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import br.com.fiap.epictask.service.TokenService;

public class AuthorizationFilter extends OncePerRequestFilter{//Executado 1 vez por requisicao

	//Nao usamos o autowired pois o spring nao reconheceu como bean
	private TokenService tokenService;

	private UserRepository repository;

	
	public AuthorizationFilter(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//extrair o token do header
		String token = extractToken(request);
		
		System.out.println(token);

		//validar o token
		boolean valid = tokenService.valid(token);

		System.out.println(valid);
		
		//se o token e valido, autorize
		if(valid) {
			authorize(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authorize(String token) {
		Long id = tokenService.getUserId(token);
		User user = repository.findById(id).get();
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");

		if(header == null || header.isEmpty() || !header.startsWith("Bearer ")){ //tratando possiveis erros
			return null;
		}
		
		return header.substring(7, header.length()); //removendo o "Bearer "
	} 

}
