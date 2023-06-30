package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.GetJsonClient;
import ru.sber.model.LimitedClient;
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

    public ClientController(ClientInterfaceService clientInterfaceService) {
        this.clientInterfaceService = clientInterfaceService;
    }


    @PostMapping
    public ResponseEntity<?> addClient(@RequestBody GetJsonClient client) {
        log.info("Добавляет клиента: {}", client);

        return ResponseEntity.created(URI.create("client/" +
                clientInterfaceService.addClient(client))).build();
    }

    @GetMapping
    public ResponseEntity<LimitedClient> getClient(@RequestParam long id) {
        log.info("Получает клиента с id {}", id);

        var client = clientInterfaceService.getClientById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok().body(new LimitedClient(client));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestParam long id) {
        log.info("Удаляет клиента с id {}", id);

        boolean isDeleted = clientInterfaceService.deleteClientById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
