package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entity.ProductBasket;
import ru.sber.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с корзиной
 */
@Repository
public interface BasketRepository extends JpaRepository<ProductBasket, Long> {
    /**
     * Проверяет есть ли у клиента корзина
     *
     * @param idUser id клиента
     * @return boolean
     */
    boolean existsByUserId(long idUser);

    /**
     * Проверяет продукты в корзине на наличие
     *
     * @param idUser    id клиента
     * @param idProduct id продукта
     * @return boolean
     */
    boolean existsByUserIdAndProductId(long idUser, long idProduct);

    /**
     * Получает товар в корзине по id клиента
     *
     * @param idUser id клиента
     * @return List<ProductBasket>
     */
    List<ProductBasket> findByUserId(long idUser);

    /**
     * Удаляет корзину клиента
     *
     * @param user клиент
     */
    void deleteAllByUser(User user);

    /**
     * Ищет продукт в корзине по id клиента и id продукта
     *
     * @param idUser    id клиента
     * @param idProduct id продукта
     * @return Optional<ProductBasket>
     */
    Optional<ProductBasket> findByUserIdAndProductId(long idUser, long idProduct);
}
