package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Client;
import ru.sber.model.LimitedClient;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ClientRepository;

@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {
    private final ClientRepository clientRepository;
    private BasketRepository basketRepository;

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
        var client = clientRepository.getClientById(id);

        if (client.isPresent()) {
            log.info("Получение клиента с id {}: {}", id, client);

            return ResponseEntity.ok().body(new LimitedClient(client));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable long id) {
        boolean isDeleted = clientRepository.deleteById(id);

        if (isDeleted) {
            log.info("Удаление клиента с id {}", id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
