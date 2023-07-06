package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.User;
import ru.sber.model.LimitedUser;
import ru.sber.services.BasketInterfaceService;
import ru.sber.services.UserInterfaceService;

/**
 * Получает запросы для взаимодействия с клиентом
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("users")
public class UserController {
    private final UserInterfaceService userInterfaceService;
    private final BasketInterfaceService basketInterfaceService;

    public UserController(UserInterfaceService userInterfaceService, BasketInterfaceService basketInterfaceService) {
        this.userInterfaceService = userInterfaceService;
        this.basketInterfaceService = basketInterfaceService;
    }

    //    @PostMapping
//    public ResponseEntity<Void> addUser(@RequestBody User user) throws URISyntaxException {
//        log.info("Добавляет клиента: {}", user);
//
//        return ResponseEntity
//                .created(new URI("users/" + userDetailsService.(user)))
//                .build();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<LimitedUser> getUserById(@PathVariable long id) {
        log.info("Получает клиента с id {}", id);

        var user = userInterfaceService.getUserById(id);
        var product = basketInterfaceService.getUserProductListById(id);

        if (user.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(new LimitedUser(user.get(), product));
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable long id) {
        log.info("Удаляет клиента с id {}", id);

        boolean isDeleted = userInterfaceService.deleteUserById(id);

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


    @GetMapping
    public ResponseEntity<Void> getUserByName(@RequestBody User user) {
        log.info("Проверяем что клиент с логином {} существует", user.getUsername());

        boolean isDeleted = userInterfaceService.existsUserByName(user);

        if (isDeleted) {
            return ResponseEntity
                    .ok()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
