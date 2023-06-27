package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.repository.ClientRepository;

@Service
public class PaymentService implements PaymentInterfaceService{
    private ClientRepository clientRepository;

    public PaymentService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }



}
