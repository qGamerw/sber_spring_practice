package ru.sber.repository;

import ru.sber.model.Client;

import java.util.Optional;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface ClientRepository {
    /**
     * Добавляет клиента
     *
     * @param client клиент
     * @return индефикатор клиента
     */
    long add(Client client);

    /**
     * Выводит клиента по индикатору
     *
     * @param id клиент
     * @return результат
     */
    Optional<Client> getClientById(long id);

    /**
     * Удаляет клиента
     *
     * @param id клиент
     * @return результат
     */
    boolean deleteById(long id);

}
