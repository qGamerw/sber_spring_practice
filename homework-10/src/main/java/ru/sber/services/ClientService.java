package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final BasketInterfaceService basketInterfaceService;
    private final PromoCodeInterfaceService promoCodeInterfaceService;

    public ClientService(ClientRepository clientRepository,
                         BasketInterfaceService basketInterfaceService,
                         PromoCodeInterfaceService promoCodeInterfaceService) {

        this.clientRepository = clientRepository;
        this.basketInterfaceService = basketInterfaceService;
        this.promoCodeInterfaceService = promoCodeInterfaceService;
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

        return clientRepository.findById(id);
    }

    @Transactional
    @Override
    public boolean deleteClientById(long id) {
        log.info("ClientService удаляет клиента с id {}", id);

        basketInterfaceService.deleteBasket(id);
        clientRepository.deleteById(id);

        return true;
    }

    @Override
    public BigDecimal getPrice(long idClient, long idPromoCode) {
        log.info("ClientService получает сумма к оплате у клиента id {} с учетом скидки", idClient);

        var price = clientRepository.findById(idClient).get().getPrice();
        var promoCode = promoCodeInterfaceService.getPromoCodeById(idPromoCode)
                .get().getDiscount();

        var discount = promoCode / 100;

        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)));
    }

    @Override
    public long getIdCard(long id) {
        log.info("ClientService получает id карты у клиента id {}", id);

        return clientRepository.findById(id).get().getIdCard();
    }
}
