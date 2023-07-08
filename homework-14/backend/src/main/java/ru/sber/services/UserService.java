package ru.sber.services;

import ru.sber.entities.User;

import java.util.Optional;

/**
 * Интерфейс для взаимодействия с пользователем
 */
public interface UserService {

    /**
     * Регистрирует пользователя
     *
     * @param user Данные о пользователе
     * @return long
     */
    long addUser(User user);

    /**
     * Производит поиск пользователя по id
     *
     * @param userId id пользователя
     * @return Optional<User>
     */
    Optional<User> getUserById(long userId);

    /**
     * Проверяет есть ли пользователь по id
     *
     * @param userId id пользователя
     * @return boolean
     */
    boolean checkUserExistence(long userId);

    /**
     * Удаляет пользователя по id
     *
     * @param userId id пользователя
     * @return boolean
     */
    boolean deleteUserById(long userId);

    /**
     * Ищем пользователя по логину и паролю
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return Optional<User>
     */
    Optional<User> findByLoginAndPassword(String login, String password);
}
