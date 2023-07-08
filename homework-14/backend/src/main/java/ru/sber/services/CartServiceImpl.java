package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.Cart;
import ru.sber.entities.Product;
import ru.sber.entities.User;
import ru.sber.repositories.CartRepository;
import ru.sber.repositories.ProductRepository;
import ru.sber.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с корзиной пользователя
 */
@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean addToCart(long userId, long productId, int amount) {

        if (!productRepository.existsById(productId) || !userRepository.existsById(userId)) {
            return false;
        }

        Optional<Cart> cart = cartRepository.findCartByProduct_IdAndClient_Id(productId, userId);

        Cart shoppingCart;

        if (cart.isPresent()) {
            shoppingCart = cart.get();
            shoppingCart.setAmount(shoppingCart.getAmount() + amount);
        } else {
            shoppingCart = new Cart();
            User client = new User();
            Product product = new Product();
            client.setId(userId);
            product.setId(productId);
            shoppingCart.setClient(client);
            shoppingCart.setProduct(product);
            shoppingCart.setAmount(amount);
        }
        cartRepository.save(shoppingCart);

        return true;

    }

    @Override
    public boolean updateProductAmount(long userId, long productId, int amount) {
        Optional<Cart> cart = cartRepository.findCartByProduct_IdAndClient_Id(productId, userId);
        if (cart.isPresent()) {
            Cart shoppingCart = cart.get();
            shoppingCart.setAmount(amount);
            cartRepository.save(shoppingCart);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteProduct(long userId, long productId) {
        Optional<Cart> cart = cartRepository.findCartByProduct_IdAndClient_Id(productId, userId);
        if (cart.isPresent()) {
            long idCart = cart.get().getId();
            cartRepository.deleteById(idCart);
            return true;
        }

        return false;
    }

    @Override
    public void clearCart(long userId) {
        cartRepository.deleteCartByClient_Id(userId);
    }

    @Override
    public List<Product> getListOfProductsInCart(long userId) {
        List<Cart> carts = cartRepository.findCartByClient_Id(userId);
        List<Product> productsInCart = new ArrayList<>();
        for (Cart cart : carts) {
            Product product = new Product();
            product.setId(cart.getProduct().getId());
            product.setName(cart.getProduct().getName());
            product.setPrice(cart.getProduct().getPrice());
            product.setAmount(cart.getAmount());
            productsInCart.add(product);
        }

        return productsInCart;
    }

    @Override
    public int countProductsInCart(long userId) {
        return cartRepository.countCartsByClient_Id(userId);
    }

}
