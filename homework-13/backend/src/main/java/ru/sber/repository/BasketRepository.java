package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.Client;
import ru.sber.entity.ProductBasket;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketRepository extends JpaRepository<ProductBasket, Long> {
    /**
     * Проверяет есть ли у клиента корзина
     *
     * @param idClient id клиента
     * @return boolean
     */
    boolean existsByClientId(long idClient);

    /**
     * Проверяет продукты в корзине на наличие
     *
     * @param idClient  id клиента
     * @param idProduct id продукта
     * @return boolean
     */
    boolean existsByClientIdAndProductId(long idClient, long idProduct);

    /**
     * Получает товар в корзине по id клиента
     *
     * @param idClient id клиента
     * @return List<ProductBasket>
     */
    List<ProductBasket> findByClientId(long idClient);

    /**
     * Удаляет корзину клиента
     *
     * @param client клиент
     */
    void deleteAllByClient(Client client);

    /**
     * Ищет продукт в корзине по id клиента и id продукта
     *
     * @param idClient  id клиента
     * @param idProduct id продукта
     * @return Optional<ProductBasket>
     */
    Optional<ProductBasket> findByClientIdAndProductId(long idClient, long idProduct);
}
