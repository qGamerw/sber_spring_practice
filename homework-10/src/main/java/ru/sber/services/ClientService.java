package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.entity.Client;
import ru.sber.repository.ClientRepository;

import java.math.BigDecimal;
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
    public long addClient(Client client) {
        log.info("ClientService добавляет клиента с id {}", client);

        Client newClient = new Client(0,
                client.getName(), client.getEmail(), client.getIdCard(),
                client.getUsername(), client.getPassword(), BigDecimal.ZERO);

        return clientRepository.save(newClient).getId();
    }

    @Override
    public Optional<Client> getClientById(long id) {
        log.info("ClientService получает клиента с id {}", id);

        Optional<Client> client = clientRepository.findById(id);

        return clientRepository.findById(id);
    }

    @Override
    public boolean deleteClientById(long id) {
        log.info("ClientService удаляет клиента с id {}", id);

        clientRepository.deleteById(id);

        return true;
    }
}
