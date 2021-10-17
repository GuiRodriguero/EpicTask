package br.com.fiap.epictask.controller;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private RegisterService service;

    @PostMapping("register")
    public String save(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        return service.save(user, result, redirect);
    }

}
