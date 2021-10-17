package br.com.fiap.epictask.service;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MessageSource message;


    public String create() {//Método que mostra a tela de criação de tarefas
        return "user-form";
    }


    public String save(User user, BindingResult result, RedirectAttributes redirect) {
        if(result.hasErrors()) return "user-form";
        user.setPassword(
                AuthenticationService
                        .getPasswordEncoder()
                        .encode(user.getPassword())
        );
        System.out.println(user);

        redirect.addFlashAttribute("message", message.getMessage("newuser.success", null, LocaleContextHolder.getLocale()));
        repository.save(user);
        return "redirect:/users";
    }

    public String delete(Long id) {
        repository.deleteById(id);
        return "redirect:/users";
    }

    public List<User> findAll() {
        return repository.findAll();
    }

}
