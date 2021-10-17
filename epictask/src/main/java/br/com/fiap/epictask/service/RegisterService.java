package br.com.fiap.epictask.service;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class RegisterService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MessageSource message;

    public String save(User user, BindingResult result, RedirectAttributes redirect) {
        if(result.hasErrors()) return "user-form";

        user.setPassword(
                AuthenticationService
                        .getPasswordEncoder()
                        .encode(user.getPassword())
        );
        redirect.addFlashAttribute("message", message.getMessage("newuser.success", null, LocaleContextHolder.getLocale()));
        repository.save(user);
        return "redirect:/";
    }
}
