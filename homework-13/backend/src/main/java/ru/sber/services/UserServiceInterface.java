package ru.sber.services;

import ru.sber.entity.User;

import java.util.Optional;

public interface UserServiceInterface {

    /**
     * Регистрирует пользователя
     *
     * @param user клиент
     * @return long
     */
    long signUp(User user);

    /**
     * Производит поиск пользователя по id
     *
     * @param userId id пользователя
     * @return Optional<User>
     */
    Optional<User> getUserById(long userId);

    /**
     * Проверяет есть ли пользователь
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
     * Ищет пользователя по логину и паролю
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return Optional<User>
     */
    Optional<User> findByLoginAndPassword(String login, String password);
}
