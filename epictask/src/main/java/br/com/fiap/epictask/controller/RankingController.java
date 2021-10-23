package br.com.fiap.epictask.controller;

import br.com.fiap.epictask.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private RankingService service;

    @GetMapping
    public ModelAndView index() {
        return service.index();
    }

}
