package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.AbridgedUser;
import ru.sber.entity.User;
import ru.sber.services.BasketServiceInterface;
import ru.sber.services.UserServiceInterface;

import java.net.URI;
import java.util.Optional;

/**Контроллер для взаимодействия с клиентом*/
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("users")
public class UserController {

    private final UserServiceInterface userServiceInterface;
    private final BasketServiceInterface basketServiceInterface;

    @Autowired
    public UserController(UserServiceInterface userServiceInterface, BasketServiceInterface basketServiceInterface) {
        this.userServiceInterface = userServiceInterface;
        this.basketServiceInterface = basketServiceInterface;
    }

    /**
     * Регистрирует нового пользователя
     *
     * @param user Пользователь
     * @return Возвращает идентификатор зарегистрированного пользователя
     */

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) {
        long userId = userServiceInterface.signUp(user);
        log.info("Регистрация пользователя {}", user);
        return ResponseEntity.created(URI.create("users/" + userId)).build();
    }

    /**
     * Находит пользователя по идентификатору
     *
     * @param id Уникальный идентификатор пользователя
     * @return Возвращает пользователя с ограниченным количеством полей
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AbridgedUser> getUserById(@PathVariable long id) {

        log.info("Выводим данные о пользователе с id: {}", id);

        Optional<User> user = userServiceInterface.getUserById(id);

        if (user.isPresent()) {
            AbridgedUser abridgedUser = new AbridgedUser(user.get());
            abridgedUser.setCart(basketServiceInterface.getListOfProductsInCart(id));
            return ResponseEntity.ok().body(abridgedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удаляет пользователя по идентификатору
     *
     * @param id Уникальный идентификатор пользователя
     * @return Возвращает статус выполнения
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {

        log.info("Удаляем пользователя с id: {}", id);

        boolean isDeleted = userServiceInterface.deleteUserById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> getUserByLoginAndPassword(@RequestParam("login") String login,
                                                       @RequestParam("password") String password) {

        log.info("Проверяем есть ли пользователь с переданными логином и паролем");

        Optional<User> checkUser = userServiceInterface.findByLoginAndPassword(login, password);

        if (checkUser.isPresent()) {
            long id = checkUser.get().getId();
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
