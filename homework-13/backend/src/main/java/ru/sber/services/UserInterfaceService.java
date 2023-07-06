package ru.sber.services;


import ru.sber.entity.User;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface UserInterfaceService {
//    /**
//     * Создает клиента
//     *
//     * @param user клиент
//     * @return long
//     */
//    long addUser(User user);

    /**
     * Получает клиента по id
     *
     * @param id клиент
     * @return Optional<User>
     */
    Optional<User> getUserById(long id);

    /**
     * Удаляет клиента по id
     *
     * @param id клиент
     * @return boolean
     */
    boolean deleteUserById(long id);

    /**
     * Получает сумму к оплате
     *
     * @param idUser      id клиента
     * @param idPromoCode id промокода
     * @return BigDecimal
     */
    BigDecimal getPrice(long idUser, long idPromoCode);

    /**
     * Получает карту клиента
     *
     * @param id id клиента
     * @return boolean
     */
    long getIdCard(long id);

    /**
     * Проверяем есть ли такой клиент клиента
     *
     * @param user клиент
     * @return boolean
     */
    boolean existsUserByName(User user);
}
