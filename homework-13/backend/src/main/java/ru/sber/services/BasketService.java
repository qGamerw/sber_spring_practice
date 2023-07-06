package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entity.Product;
import ru.sber.entity.ProductBasket;
import ru.sber.entity.User;
import ru.sber.model.LimitedProduct;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ProductRepository;
import ru.sber.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

/**
 * Класс для взаимодействия с корзиной
 */
@Slf4j
@Service
public class BasketService implements BasketInterfaceService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository) {

        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    private static Predicate<Product> getProductPredicate(ProductBasket productInBasket) {
        return product -> product.getName().equals(productInBasket.getProduct().getName())
                && product.getPrice().compareTo(productInBasket.getProduct().getPrice()) == 0;
    }

    @Override
    public boolean add(long idUser, Product product) {
        log.info("BasketService добавляет продукт {} в корзине id  {}", product, idUser);

        var isProduct = productRepository.existsById(product.getId());
        var isBasket = basketRepository.existsByUserIdAndProductId(idUser, product.getId());
        var isUser = userRepository.existsById(idUser);

        var user = userRepository.findById(idUser);

        if (isProduct && isUser && !isBasket) {

            ProductBasket productBasket = new ProductBasket(0, product,
                    user.get(), product.getAmount());

            updateUserTotalPrice(product, user.get(), productBasket);

            return true;
        } else if (isProduct && isUser) {

            var productBasket = basketRepository.findByUserIdAndProductId(idUser, product.getId()).get();
            productBasket.setAmount(product.getAmount() + productBasket.getAmount());

            updateUserTotalPrice(product, user.get(), productBasket);

            return true;
        }

        return false;
    }

    @Override
    public boolean updateProduct(long idUser, Product product) {
        log.info("BasketService обновляет продукт {} в корзине id {}", idUser, product);

        var isBasket = basketRepository.existsByUserIdAndProductId(idUser, product.getId());

        var user = userRepository.findById(idUser);
        var productBasket = basketRepository.findByUserIdAndProductId(idUser, product.getId());

        if (isBasket) {
            var priceOldProduct = productBasket.get().getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(productBasket.get().getAmount()));

            productBasket.get().setAmount(product.getAmount());
            basketRepository.save(productBasket.get());

            var totalPrice = productRepository.findById(product.getId()).get().getPrice()
                    .multiply(BigDecimal.valueOf(product.getAmount()))
                    .add(user.get().getPrice())
                    .subtract(priceOldProduct);

            user.get().setPrice(totalPrice);
            userRepository.save(user.get());

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteProduct(long idUser, Product product) {
        log.info("BasketService удаляет продукт {} в корзине id {}", product, idUser);

        var isBasket = basketRepository.existsByUserIdAndProductId(idUser, product.getId());

        var productBasket = basketRepository.findByUserIdAndProductId(idUser, product.getId());
        var user = userRepository.findById(idUser);

        if (isBasket) {
            basketRepository.deleteById(productBasket.get().getId());

            var totalPrice = user.get().getPrice()
                    .subtract(productBasket.get().getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(productBasket.get().getAmount())));

            user.get().setPrice(totalPrice);
            userRepository.save(user.get());

            return true;
        }
        return false;
    }

    @Override
    public boolean isBasket(long user) {
        log.info("BasketService проверяет есть ли у клиента корзина {}", user);

        return basketRepository.existsByUserId(user);
    }

    @Override
    public boolean isCountProduct(User user) {
        log.info("BasketService проверяет достаточно ли товара у клиента id {}", user.getId());

        var productBasketList = basketRepository.findByUserId(user.getId());

        for (ProductBasket productBasket : productBasketList) {
            var product = productRepository.findById(productBasket.getProduct().getId());

            if (productBasket.getAmount() > product.get().getAmount()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean basketCleaning(long idUser) {
        log.info("BasketService отчищает корзину id {} при оплате", idUser);

        updateAmountProduct(idUser);
        var user = userRepository.findById(idUser);

        if (user.isPresent()) {
            user.get().setPrice(BigDecimal.ZERO);
            userRepository.save(user.get());

            basketRepository.deleteAllByUser(user.get());
            return true;
        }

        return false;
    }

    @Override
    public List<LimitedProduct> getUserProductListById(long id) {
        log.info("BasketService получаем список продуктов у клиента с id {}", id);

        return basketRepository.findByUserId(id)
                .stream()
                .map(LimitedProduct::new)
                .toList();
    }

    @Override
    public boolean deleteBasket(long id) {
        log.info("BasketService удаляет корзину клиента с id {}", id);

        var user = new User();
        user.setId(id);
        basketRepository.deleteAllByUser(user);

        return true;
    }

    private void updateUserTotalPrice(Product product, User user, ProductBasket productBasket) {
        basketRepository.save(productBasket);

        var totalPrice = productRepository.findById(product.getId()).get().getPrice()
                .multiply(BigDecimal.valueOf(product.getAmount()))
                .add(user.getPrice());

        user.setPrice(totalPrice);
        userRepository.save(user);
    }

    private boolean updateAmountProduct(long id) {
        log.info("Обновляет количество товара на складе");

        var productBasketList = basketRepository.findByUserId(id);

        for (ProductBasket productBasket : productBasketList) {

            var product = productRepository.findById(productBasket.getProduct().getId()).get();
            product.setAmount(product.getAmount() - productBasket.getAmount());
            productRepository.save(product);
        }
        return true;
    }
}
