package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Basket;
import ru.sber.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Класс для взаимодействия с клиентом
 */
@Repository
public class LocalClientRepository implements ClientRepository {
    private final List<Client> clients = new ArrayList<>();
    private final BasketRepository basketRepository;

    public LocalClientRepository(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public long add(Client client) {
        client.setId(generateLongId());
        client.setBasket(new Basket(basketRepository.generateLongId(), basketRepository.getListBasket(), generateIntId()));
        clients.add(client);
        return client.getId();
    }

    @Override
    public Optional<Client> getClientById(long id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findAny();
    }

    @Override
    public boolean deleteById(long id) {
        return clients.removeIf(client -> client.getId() == id);
    }

    private long generateLongId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }

    private int generateIntId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextInt(high - low) + low;
    }
}
