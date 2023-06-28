package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Client;
import ru.sber.model.LimitedClient;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ClientRepository;

/**
 * Получает запросы для взаимодействия с клиентом
 */
@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {
    private final ClientRepository clientRepository;
    private final BasketRepository basketRepository;

    public ClientController(ClientRepository clientRepository, BasketRepository basketRepository) {
        this.clientRepository = clientRepository;
        this.basketRepository = basketRepository;
    }

    @PostMapping
    public long addClient(@RequestBody Client client) {
        log.info("Добавление клиента: {}", client);
        return clientRepository.add(client);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LimitedClient> getClient(@PathVariable long id) {
        log.info("Получение клиента с id {}", id);
        var client = clientRepository.getClientById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok().body(new LimitedClient(client));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable long id) {
        log.info("Удаление клиента с id {}", id);
        boolean isDeleted = clientRepository.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
