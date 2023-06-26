package ru.sber.model;

/**
 * Класс для описания пользователя
 */
public record User(String name, String email, String password, String typeSignIn) {
}
