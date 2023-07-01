package ru.sber.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.Client;

/**
 * Интерфейс для взаимодействия с клиентом
 */
public interface ClientRepository extends JpaRepository<Client, Long> {


//    /**
//     * Добавляет клиента
//     *
//     * @param client клиент
//     * @return long
//     */
//    long add(Client client);
//
//    /**
//     * Выводит клиента по индикатору
//     *
//     * @param id клиент
//     * @return Optional<Client>
//     */
//    Optional<Client> getClientById(long id);
//
//    /**
//     * Удаляет клиента
//     *
//     * @param id клиент
//     * @return boolean
//     */
//    boolean deleteById(long id);
}
