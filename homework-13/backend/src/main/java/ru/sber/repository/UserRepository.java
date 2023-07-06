package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entity.User;

import java.util.Optional;

/**
 * Интерфейс для взаимодействия с клиентом
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
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

    Optional<User> findByUsername(String username);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);

}
