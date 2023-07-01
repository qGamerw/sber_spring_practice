package ru.sber.services;

import ru.sber.entity.Client;

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
}
