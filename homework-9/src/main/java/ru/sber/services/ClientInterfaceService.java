package ru.sber.services;

import ru.sber.model.Client;
import ru.sber.model.GetJsonClient;

import java.util.Optional;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface ClientInterfaceService {
    /**
     * @param client клиент
     * @return long
     */
    long addClient(GetJsonClient client);

    /**
     * @param id клиент
     * @return Optional<Client>
     */
    Optional<Client> getClientById(long id);

    /**
     * @param id клиент
     * @return boolean
     */
    boolean deleteClientById(long id);
}
