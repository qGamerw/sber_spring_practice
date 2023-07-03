package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entity.Client;
import ru.sber.entity.Product;
import ru.sber.entity.ProductBasket;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ClientRepository;
import ru.sber.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Класс для взаимодействия с корзиной
 */
@Slf4j
@Service
public class BasketService implements BasketInterfaceService {

    private final BasketRepository basketRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository,
                         ClientRepository clientRepository,
                         ProductRepository productRepository) {

        this.basketRepository = basketRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    private static Predicate<Product> getProductPredicate(ProductBasket productInBasket) {
        return product -> product.getName().equals(productInBasket.getProduct().getName())
                && product.getPrice().compareTo(productInBasket.getProduct().getPrice()) == 0;
    }

    @Override
    public boolean add(long idClient, Product product) {
        log.info("BasketService добавляет продукт {} в корзине id  {}", product, idClient);

        var isProduct = productRepository.existsById(product.getId());
        var isBasket = basketRepository.existsByClientIdAndProductId(idClient, product.getId());
        var isClient = clientRepository.existsById(idClient);

        var client = clientRepository.findById(idClient);

        if (isProduct && isClient && !isBasket) {

            ProductBasket productBasket = new ProductBasket(0, product,
                    client.get(), product.getAmount());

            updateClientTotalPrice(product, client.get(), productBasket);

            return true;
        } else if (isProduct && isClient) {

            var productBasket = basketRepository.findByClientIdAndProductId(idClient, product.getId()).get();
            productBasket.setAmount(product.getAmount() + productBasket.getAmount());

            updateClientTotalPrice(product, client.get(), productBasket);

            return true;
        }

        return false;
    }

    @Override
    public boolean updateProduct(long idClient, Product product) {
        log.info("BasketService обновляет продукт {} в корзине id {}", idClient, product);

        var isBasket = basketRepository.existsByClientIdAndProductId(idClient, product.getId());

        var client = clientRepository.findById(idClient);
        var productBasket = basketRepository.findByClientIdAndProductId(idClient, product.getId());

        if (isBasket) {
            var priceOldProduct = productBasket.get().getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(productBasket.get().getAmount()));

            productBasket.get().setAmount(product.getAmount());
            basketRepository.save(productBasket.get());

            var totalPrice = productRepository.findById(product.getId()).get().getPrice()
                    .multiply(BigDecimal.valueOf(product.getAmount()))
                    .add(client.get().getPrice())
                    .subtract(priceOldProduct);

            client.get().setPrice(totalPrice);
            clientRepository.save(client.get());

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteProduct(long idClient, Product product) {
        log.info("BasketService удаляет продукт {} в корзине id {}", product, idClient);

        var isBasket = basketRepository.existsByClientIdAndProductId(idClient, product.getId());

        var productBasket = basketRepository.findByClientIdAndProductId(idClient, product.getId());
        var client = clientRepository.findById(idClient);

        if (isBasket) {
            basketRepository.deleteById(productBasket.get().getId());

            var totalPrice = client.get().getPrice()
                    .subtract(productBasket.get().getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(productBasket.get().getAmount())));

            client.get().setPrice(totalPrice);
            clientRepository.save(client.get());

            return true;
        }
        return false;
    }

    @Override
    public boolean isBasket(long client) {
        log.info("BasketService проверяет есть ли у клиента корзина {}", client);

        return basketRepository.existsByClientId(client);
    }

    @Override
    public boolean isCountProduct(Client client) {
        log.info("BasketService проверяет достаточно ли товара у клиента id {}", client.getId());

        var productBasketList = basketRepository.findByClientId(client.getId());

        for (ProductBasket productBasket : productBasketList) {
            var product = productRepository.findById(productBasket.getProduct().getId());

            if (productBasket.getAmount() > product.get().getAmount()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean basketCleaning(long idClient) {
        log.info("BasketService отчищает корзину id {} при оплате", idClient);

        updateAmountProduct(idClient);
        var client = clientRepository.findById(idClient);

        if (client.isPresent()) {
            client.get().setPrice(BigDecimal.ZERO);
            clientRepository.save(client.get());

            basketRepository.deleteAllByClient(client.get());
            return true;
        }

        return false;
    }

    @Override
    public List<Product> getClientUnrepeatableProductListById(long id) {
        log.info("BasketService получаем неповторяющийся список продуктов у клиента с id {}", id);

        List<ProductBasket> productBasketList = basketRepository.findByClientId(id);
        List<Product> clientProductList = new ArrayList<>();

        for (ProductBasket productInBasket : productBasketList) {
            productInBasket.getProduct().setAmount(productInBasket.getAmount());

            var productAtClient = clientProductList.stream()
                    .filter(getProductPredicate(productInBasket))
                    .findAny();

            if (productAtClient.isPresent()) {
                clientProductList.stream()
                        .filter(prod -> prod == productAtClient.get())
                        .findAny()
                        .get()
                        .setAmount(productAtClient.get().getAmount() + productInBasket.getAmount());
            } else {
                clientProductList.add(productInBasket.getProduct());
            }
        }

        return clientProductList;
    }

    @Override
    public boolean deleteBasket(long id) {
        log.info("BasketService удаляет корзину клиента с id {}", id);

        var client = new Client();
        client.setId(id);
        basketRepository.deleteAllByClient(client);

        return true;
    }

    private void updateClientTotalPrice(Product product, Client client, ProductBasket productBasket) {
        basketRepository.save(productBasket);

        var totalPrice = productRepository.findById(product.getId()).get().getPrice()
                .multiply(BigDecimal.valueOf(product.getAmount()))
                .add(client.getPrice());

        client.setPrice(totalPrice);
        clientRepository.save(client);
    }

    private boolean updateAmountProduct(long id) {
        log.info("Обновляет количество товара на складе");

        var productBasketList = basketRepository.findByClientId(id);

        for (ProductBasket productBasket : productBasketList) {

            var product = productRepository.findById(productBasket.getProduct().getId()).get();
            product.setAmount(product.getAmount() - productBasket.getAmount());
            productRepository.save(product);
        }
        return true;
    }
}
