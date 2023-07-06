package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.Client;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Проверяет есть ли клиент
     *
     * @param id id клиента
     * @return boolean
     */
    boolean existsById(long id);

    /**
     * Проверяет есть ли клиент
     *
     * @param username id клиента
     * @return boolean
     */
    boolean existsByUsername(String username);
}
