package br.com.fiap.epictask.service;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class RankingService {

    @Autowired
    private UserRepository repository;

    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("ranking");
        List<User> users = repository.findAllByOrderByPointsDesc();
        modelAndView.addObject("users", users);
        System.out.println(users);
        return modelAndView;
    }
}
