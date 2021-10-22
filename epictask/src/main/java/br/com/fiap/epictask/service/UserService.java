package br.com.fiap.epictask.service;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MessageSource message;

    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("users");
        List<User> users = findAll();
        modelAndView.addObject("users", users);
        System.out.println(users);
        return modelAndView;
    }

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

    public String delete(Long id, RedirectAttributes redirect) {
        repository.deleteById(id);
        redirect.addFlashAttribute("message", message.getMessage("deleteuser.success", null, LocaleContextHolder.getLocale()));
        return "redirect:/user";
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public ModelAndView editView(Long id) {
        ModelAndView modelAndView = new ModelAndView("user-form-update");
        Optional<User> user = repository.findById(id);
        user.ifPresent(obj -> modelAndView.addObject("user", obj));
        return modelAndView;
    }

    public String edit(User newUser, BindingResult result, RedirectAttributes redirect) {
        Optional<User> optional = repository.findByEmail(newUser.getEmail());

        if(result.hasErrors()) return "user-form-update";
        newUser.setPassword(
                AuthenticationService
                        .getPasswordEncoder()
                        .encode(newUser.getPassword())
        );

        User user = optional.get();
        user.setName(newUser.getName());
        user.setGithubuser(newUser.getGithubuser());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());

        redirect.addFlashAttribute("message", message.getMessage("edituser.success", null, LocaleContextHolder.getLocale()));
        repository.save(newUser);
        return "redirect:/user";
    }
}
