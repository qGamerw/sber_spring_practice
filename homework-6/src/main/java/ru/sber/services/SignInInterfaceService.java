package ru.sber.services;

import ru.sber.model.User;

import java.util.List;

/**
 * Интерфейс для входа и регистрации пользователей
 */
public interface SignInInterfaceService {
    /**
     * Добавляет зарегистрированного пользователя
     */
    List<User> addUser(User user);

    /**
     * Проверяет зарегистрирован ли пользователь
     */
    boolean isUser(User user);

    /**
     * Меняет состояние пользователя при выходе
     */
    List<User> signOutUser(String login);

    /**
     * Получает список зарегистрированных пользователей
     */
    List<User> getUsers();
}
