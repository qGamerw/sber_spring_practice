package ru.sber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.model.User;
import ru.sber.services.SignInInterfaceService;
import ru.sber.services.SignInService;

/**
 * Класс контроллер для работы с входом и регистрацией
 */
@Controller
public class SignInController {

    private final SignInInterfaceService signInInterfaceService;

    public SignInController(SignInService signInInterfaceService) {
        this.signInInterfaceService = signInInterfaceService;
    }

    @GetMapping("/home")
    public String temporaryUser(@RequestParam(required = false) String name, Model model) {
        if (name != null) {
            var users = signInInterfaceService.getUsers();
            signInInterfaceService.addUser(new User(name, "", "", "Temporary"));
            model.addAttribute("listusers", users);
        }
        return "home2.html";
    }


    @PostMapping("/home")
    public String registrationUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ) {
        if (login != null && password != null && email.equals(" ")) {
            signInInterfaceService.isUser(new User(login, "", password, "Sign in"));
            var user = signInInterfaceService.getUsers();
            model.addAttribute("listusers", user);
        } else if (login != null && password != null) {
            signInInterfaceService.addUser(new User(login, email, password, "Registration"));
            var user = signInInterfaceService.getUsers();
            model.addAttribute("listusers", user);
        }
        return "home2.html";
    }
}