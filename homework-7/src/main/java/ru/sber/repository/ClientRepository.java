package ru.sber.repository;

import ru.sber.model.Client;

import java.util.Optional;

public interface ClientRepository {
    long add(Client client);
//    long addBasket(Client client);

    Optional<Client> getClientById(long id);

    boolean deleteById(long id);

}
