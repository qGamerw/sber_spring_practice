package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Client;
import ru.sber.model.LimitedClient;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ClientRepository;

import java.net.URI;

/**
 * Получает запросы для взаимодействия с клиентом
 */
@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository, BasketRepository basketRepository) {
        this.clientRepository = clientRepository;
    }


    @PostMapping
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        log.info("Добавление клиента: {}", client);

        return ResponseEntity.created(URI.create("client/" + clientRepository.add(client))).build();
    }

    @GetMapping
    public ResponseEntity<LimitedClient> getClient(@RequestParam long id) {
        log.info("Получение клиента с id {}", id);

        var client = clientRepository.getClientById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok().body(new LimitedClient(client));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestParam long id) {
        log.info("Удаление клиента с id {}", id);

        boolean isDeleted = clientRepository.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
