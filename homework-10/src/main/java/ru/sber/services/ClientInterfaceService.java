package ru.sber.services;

import ru.sber.entity.Client;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface ClientInterfaceService {
    /**
     * Создает клиента
     *
     * @param client клиент
     * @return long
     */
    long addClient(Client client);

    /**
     * Получает клиента по id
     *
     * @param id клиент
     * @return Optional<Client>
     */
    Optional<Client> getClientById(long id);

    /**
     * Удаляет клиента по id
     *
     * @param id клиент
     * @return boolean
     */
    boolean deleteClientById(long id);

    /**
     * Получает сумму к оплате
     *
     * @param idClient    id клиента
     * @param idPromoCode id промокода
     * @return BigDecimal
     */
    BigDecimal getPrice(long idClient, long idPromoCode);

    /**
     * Получает карту клиента
     *
     * @param id id клиента
     * @return boolean
     */
    long getIdCard(long id);
}
