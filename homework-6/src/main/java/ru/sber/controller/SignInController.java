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
    private boolean isSign = true;

    public SignInController(SignInService signInInterfaceService) {
        this.signInInterfaceService = signInInterfaceService;
    }

    /**
     * Регистрируем вход временного пользователя через строку поиска
     */
    @GetMapping("/home")
    public String temporaryUser(@RequestParam(required = false) String name, Model model) {
        if (name != null) {
            signInInterfaceService.addUser(new User(name, "", "", "Temporary"));
            var users = signInInterfaceService.getUsers();

            model.addAttribute("listusers", users);
            model.addAttribute("login", name);
            model.addAttribute("isSign", isSign = true);
        }
        return "home2.html";
    }

    /**
     * Регистрируем вход пользователя через форму
     */
    @PostMapping("/home2")
    public String registrationUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ) {
        if (login != null && password != null && email.equals(" ")) {
            signInInterfaceService.isUser(new User(login, "", password, "Sign in"));
            var user = signInInterfaceService.getUsers();

            model.addAttribute("login", login);
            model.addAttribute("listusers", user);
            model.addAttribute("isSign", isSign = true);
        } else if (login != null && password != null) {
            signInInterfaceService.addUser(new User(login, email, password, "Registration"));
            var user = signInInterfaceService.getUsers();

            model.addAttribute("login", login);
            model.addAttribute("listusers", user);
            model.addAttribute("isSign", isSign = true);
        }
        return "home2.html";
    }

    /**
     * Регистрируем выход пользователя через форму
     */
    @PostMapping("/home3")
    public String signOutUser(@RequestParam String requestSignOut,
                              Model model
    ) {
        if (requestSignOut != null) {
            var user = signInInterfaceService.getUsers();
            signInInterfaceService.signOutUser(requestSignOut);

            model.addAttribute("listusers", user);
            model.addAttribute("isSign", isSign = false);
        }
        return "home2.html";
    }
}