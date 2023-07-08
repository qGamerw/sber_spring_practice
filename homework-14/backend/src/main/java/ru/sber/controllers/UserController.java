package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.LimitUser;
import ru.sber.entities.User;
import ru.sber.services.CartService;
import ru.sber.services.UserService;

import java.net.URI;
import java.util.Optional;

/**
 * Класс для взаимодействия пользователем
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Long> signUp(@Valid @RequestBody User user) {
        log.info("Создает пользователя {}", user);

        return ResponseEntity
                .created(URI.create("users/" + userService.addUser(user)))
                .build();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<LimitUser> getUserById(@PathVariable long id) {
        log.info("Выводит данные о пользователе с id {}", id);

        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            LimitUser limitUser = new LimitUser(user.get());
            limitUser.setCart(cartService.getListOfProductsInCart(id));

            return ResponseEntity
                    .ok()
                    .body(limitUser);
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        log.info("Удаляет пользователя с id {}", id);

        boolean isDeleted = userService.deleteUserById(id);

        if (isDeleted) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping()
    public ResponseEntity<Long> getUserByLoginAndPassword(@RequestParam("login") String login,
                                                          @RequestParam("password") String password) {

        log.info("Проверяет есть ли пользователь с логином и паролем");

        Optional<User> checkUser = userService.findByLoginAndPassword(login, password);

        if (checkUser.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(checkUser.get().getId());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
