package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.model.Client;
import ru.sber.model.GetJsonClient;
import ru.sber.repository.ClientRepository;

import java.util.Optional;

/**
 * Класс для взаимодействия с клиентом
 */
@Slf4j
@Service
public class ClientService implements ClientInterfaceService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public long addClient(GetJsonClient client) {
        log.info("ClientService добавляет клиента с id {}", client);

        return clientRepository.add(client);
    }

    @Override
    public Optional<Client> getClientById(long id) {
        log.info("ClientService получает клиента с id {}", id);

        return clientRepository.getClientById(id);
    }

    @Override
    public boolean deleteClientById(long id) {
        log.info("ClientService удаляет клиента с id {}", id);

        return clientRepository.deleteById(id);
    }
}
