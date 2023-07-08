package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий с корзиной клиентов
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * Выводит корзину по id товара и id клиента
     *
     * @param productId id товара
     * @param userId    id пользователя
     * @return Optional<Cart>
     */
    Optional<Cart> findCartByProduct_IdAndClient_Id(long productId, long userId);

    /**
     * Удаляет корзину по id пользователя
     *
     * @param userId id пользователя
     */
    void deleteCartByClient_Id(long userId);

    /**
     * Выводит корзину пользователя
     *
     * @param userId id пользователя
     * @return List<Cart>
     */
    List<Cart> findCartByClient_Id(long userId);

    /**
     * Считает количество товара у пользователя
     *
     * @param userId id пользователя
     * @return int
     */
    int countCartsByClient_Id(long userId);

    /**
     * Подсчитывает количество корзин, в которых есть товар
     *
     * @param productId id товара
     * @return int
     */
    int countCartsByProduct_Id(long productId);

}
