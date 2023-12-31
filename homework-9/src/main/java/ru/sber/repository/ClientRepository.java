package ru.sber.repository;

import ru.sber.model.Client;
import ru.sber.model.GetJsonClient;

import java.util.Optional;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface ClientRepository {
    /**
     * Добавляет клиента
     *
     * @param client клиент
     * @return long
     */
    long add(GetJsonClient client);

    /**
     * Выводит клиента по индикатору
     *
     * @param id клиент
     * @return Optional<Client>
     */
    Optional<Client> getClientById(long id);

    /**
     * Удаляет клиента
     *
     * @param id клиент
     * @return boolean
     */
    boolean deleteById(long id);
}
