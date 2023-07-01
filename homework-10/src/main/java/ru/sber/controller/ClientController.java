package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entity.Client;
import ru.sber.model.LimitedClient;
import ru.sber.services.BasketInterfaceService;
import ru.sber.services.ClientInterfaceService;

import java.net.URI;

/**
 * Получает запросы для взаимодействия с клиентом
 */
@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {
    private final ClientInterfaceService clientInterfaceService;
    private final BasketInterfaceService basketInterfaceService;

    public ClientController(ClientInterfaceService clientInterfaceService, BasketInterfaceService basketInterfaceService) {
        this.clientInterfaceService = clientInterfaceService;
        this.basketInterfaceService = basketInterfaceService;
    }

    @PostMapping
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        log.info("Добавляет клиента: {}", client);

        return ResponseEntity
                .created(URI.create("client/" + clientInterfaceService.addClient(client)))
                .build();
    }

    @GetMapping
    public ResponseEntity<LimitedClient> getClient(@RequestParam long id) {
        log.info("Получает клиента с id {}", id);

        var client = clientInterfaceService.getClientById(id);
        var product = basketInterfaceService.getClientById(id);

        if (client.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(new LimitedClient(client.get(), product));
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestParam long id) {
        log.info("Удаляет клиента с id {}", id);

        boolean isDeleted = clientInterfaceService.deleteClientById(id);

        if (isDeleted) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
