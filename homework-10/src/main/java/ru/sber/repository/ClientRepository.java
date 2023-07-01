package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.Client;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
