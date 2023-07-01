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
import ru.sber.repository.PromoCodeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с корзиной
 */
@Slf4j
@Service
public class BasketService implements BasketInterfaceService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final PromoCodeRepository promoCodeRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository,
                         ProductRepository productRepository,
                         ClientRepository clientRepository,
                         PromoCodeRepository promoCodeRepository) {

        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.promoCodeRepository = promoCodeRepository;
    }

    @Override
    public boolean add(long idClient, Product product) {
        log.info("BasketService добавляет продукт {} в корзине id  {}", product, idClient);

        var isProduct = getProduct(product);

        var isBasket = basketRepository.findAll()
                .stream()
                .filter(item -> item.getClient().getId() == idClient && product.getId() == item.getProduct().getId())
                .findAny();

        var isClient = clientRepository.findAll()
                .stream()
                .filter(item -> item.getId() == idClient)
                .findAny();

        if (isProduct.isPresent() && isClient.isPresent() && isBasket.isEmpty()) {
            ProductBasket productBasket = new ProductBasket(0, product,
                    isClient.get(), product.getAmount());

            basketRepository.save(productBasket);

            var price = isProduct.get().getPrice()
                    .multiply(BigDecimal.valueOf(product.getAmount()))
                    .add(isClient.get().getPrice());

            isClient.get().setPrice(price);
            clientRepository.save(isClient.get());

            return true;
        } else if (isProduct.isPresent() && isClient.isPresent()) {

            isBasket.get().setAmount(product.getAmount() + isBasket.get().getAmount());
            basketRepository.save(isBasket.get());

            var price = isProduct.get().getPrice()
                    .multiply(BigDecimal.valueOf(product.getAmount()))
                    .add(isClient.get().getPrice());

            isClient.get().setPrice(price);
            clientRepository.save(isClient.get());

            return true;
        }
        return false;
    }

    @Override
    public boolean updateProduct(long idClient, Product product) {
        log.info("BasketService обновляет продукт {} в корзине id {}", idClient, product);

        var isProduct = getProduct(product);

        var isClient = clientRepository.findAll()
                .stream()
                .filter(item -> item.getId() == idClient)
                .findAny();

        var isBasket = basketRepository.findAll()
                .stream()
                .filter(item -> item.getProduct().getId() == product.getId() && item.getClient() == isClient.get())
                .findAny();

        if (isClient.isPresent() && isBasket.isPresent()) {

            var price = isProduct.get().getPrice().multiply(BigDecimal.valueOf(isBasket.get().getAmount()));

            isBasket.get().setAmount(product.getAmount());
            basketRepository.save(isBasket.get());

            var newPrice = isProduct.get().getPrice()
                    .multiply(BigDecimal.valueOf(product.getAmount()))
                    .add(isClient.get().getPrice())
                    .subtract(price);

            isClient.get().setPrice(newPrice);
            clientRepository.save(isClient.get());

            return true;
        }
        return false;

    }

    @Override
    public boolean deleteProduct(long idClient, Product product) {
        log.info("BasketService удаляет продукт {} в корзине id {}", product, idClient);

        var isProduct = getProduct(product);

        var isClient = clientRepository.findAll()
                .stream()
                .filter(item -> item.getId() == idClient)
                .findAny();

        var isBasket = basketRepository.findAll()
                .stream()
                .filter(item -> item.getProduct().getId() == product.getId() && item.getClient() == isClient.get())
                .findAny();

        if (isClient.isPresent() && isBasket.isPresent()) {
            basketRepository.deleteById(isBasket.get().getId());

            var price = isClient.get().getPrice()
                    .subtract(isProduct.get().getPrice()
                            .multiply(BigDecimal.valueOf(isBasket.get().getAmount())));

            isClient.get().setPrice(price);
            clientRepository.save(isClient.get());

            return true;
        }
        return false;
    }

    @Override
    public boolean isBasket(long client) {
        log.info("Проверяет есть ли у клиента корзина {}", client);

        return basketRepository.existsByClientId(client);
    }

    @Override
    public boolean isCountProduct(Client client) {
        log.info("Проверяет достаточно ли товара у клиента id {}", client.getId());

        var listBasket = basketRepository.findByClientId(client.getId());

        for (ProductBasket item : listBasket) {
            var product = productRepository.findById(item.getProduct().getId());

            if (item.getAmount() > product.get().getAmount()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BigDecimal getPrice(long idClient, long idPromoCode) {
        log.info("Получает сумма к оплате у клиента id {} с учетом скидки", idClient);

        var price = clientRepository.findById(idClient).get().getPrice();
        var promoCode = promoCodeRepository.findById(idPromoCode).get().getDiscount();

        var discount = promoCode / 100;

        return price.subtract(
                price.multiply(
                        BigDecimal.valueOf(discount)));
    }

    @Override
    public long getIdCard(long id) {
        log.info("Получает id карты у клиента id {}", id);

        return clientRepository.findById(id).get().getIdCard();
    }

    @Override
    public boolean removeProductBasket(long id) {
        log.info("Удаляет товары из корзины id {}", id);

        updateAmountProduct(id);

        var client = new Client();
        client.setId(id);
        basketRepository.deleteByClient(client);

        var isClient = clientRepository.findAll()
                .stream()
                .filter(item -> item.getId() == id)
                .findAny();

        if (isClient.isPresent()) {
            isClient.get().setPrice(BigDecimal.ZERO);
            clientRepository.save(isClient.get());

            return true;
        }
        return false;
    }

    @Override
    public List<Product> getClientById(long id) {
        log.info("Получаем список продуктов у клиента с id {}", id);

        List<ProductBasket> productBaskets = basketRepository.findByClientId(id);
        List<Product> products = new ArrayList<>();

        for (ProductBasket item : productBaskets) {
            item.getProduct().setAmount(item.getAmount());

            var ind = -1;

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().equals(item.getProduct().getName())
                        && products.get(i).getPrice().compareTo(item.getProduct().getPrice()) == 0) {
                    ind = i;
                    break;
                }
            }

            if (ind != -1) {
                products.get(ind).setAmount(products.get(ind).getAmount() + item.getAmount());
            } else {
                products.add(item.getProduct());
            }
        }

        return products;
    }

    private Optional<Product> getProduct(Product product) {
        return productRepository.findAll()
                .stream()
                .filter(item -> product.getId() == item.getId())
                .findAny();
    }

    private boolean updateAmountProduct(long id) {
        log.info("Обновляет количество товара на складе");

        var listProduct = basketRepository.findByClientId(id);

        for (ProductBasket item : listProduct) {

            var product = productRepository.findById(item.getProduct().getId()).get();
            product.setAmount(product.getAmount() - item.getAmount());

            productRepository.save(product);
        }
        return true;
    }
}