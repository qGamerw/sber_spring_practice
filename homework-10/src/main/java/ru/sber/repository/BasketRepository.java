package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.Client;
import ru.sber.entity.ProductBasket;

import java.util.List;

/**
 * Интерфейс для взаимодействия с корзиной
 */
public interface BasketRepository extends JpaRepository<ProductBasket, Long> {
    /**
     * Проверяет продукты в корзине на наличие
     *
     * @param client id клиента
     * @return boolean
     */
    boolean existsByClientId(long client);

    /**
     * Получает товар в корзине по id клиента
     *
     * @param idClient id клиента
     * @return List<ProductBasket>
     */
    List<ProductBasket> findByClientId(long idClient);

    /**
     * Удаляет клиента
     *
     * @param client клиент
     */
    void deleteByClient(Client client);
}
