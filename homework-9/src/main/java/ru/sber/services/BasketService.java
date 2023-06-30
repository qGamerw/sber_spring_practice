package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.model.Product;
import ru.sber.repository.BasketRepository;

/**
 * Класс для взаимодействия с корзиной
 */
@Slf4j
@Service
public class BasketService implements BasketInterfaceService {

    private final BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public boolean addProduct(long idClient, Product product) {
        log.info("BasketService добавляет продукт {} в корзине id  {}", product, idClient);

        return basketRepository.add(idClient,
                product.getIdProduct(), product.getCount());
    }

    @Override
    public boolean updateProduct(long idClient, Product product) {
        log.info("BasketService обновляет продукт {} в корзине id {}", idClient, product);

        return basketRepository.update(idClient,
                product.getIdProduct(), product.getCount());
    }

    @Override
    public boolean deleteProduct(long idClient, Product product) {
        log.info("BasketService удаляет продукт {} в корзине id {}", product, idClient);

        return basketRepository.delete(idClient, product.getIdProduct());
    }
}
