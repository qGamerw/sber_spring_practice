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

    /**
     * Регистрирует вход временного пользователя через строку поиска
     */
    @GetMapping("/home")
    public String temporaryUser(@RequestParam(required = false) String name, Model model) {
        if (name != null) {
            signInInterfaceService.addUser(new User(name, "", "", "Temporary"));
            var users = signInInterfaceService.getUsers();

            model.addAttribute("listusers", users);
            model.addAttribute("login", name);
            model.addAttribute("isSign", true);
        }
        return "home2.html";
    }

    /**
     * Регистрирует регистрацию пользователя через форму
     */
    @PostMapping("/registration")
    public String registrationUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ) {
        signInInterfaceService.addUser(new User(login, email, password, "Registration"));
        var user = signInInterfaceService.getUsers();

        model.addAttribute("login", login);
        model.addAttribute("listusers", user);
        model.addAttribute("isSign", true);

        return "home2.html";
    }

    /**
     * Регистрирует авторизацию пользователя через форму
     */
    @PostMapping("/sign-in")
    public String signInUser(
            @RequestParam String login,
            @RequestParam String password,
            Model model
    ) {
        signInInterfaceService.isUser(new User(login, "", password, "Sign in"));
        var user = signInInterfaceService.getUsers();

        model.addAttribute("login", login);
        model.addAttribute("listusers", user);
        model.addAttribute("isSign", true);

        return "home2.html";
    }

    /**
     * Регистрируем выход пользователя через форму
     */
    @PostMapping("/sign-out")
    public String signOutUser(@RequestParam String login, Model model) {
        var user = signInInterfaceService.getUsers();
        signInInterfaceService.signOutUser(login);

        model.addAttribute("listusers", user);
        model.addAttribute("isSign", false);

        return "home2.html";
    }
}