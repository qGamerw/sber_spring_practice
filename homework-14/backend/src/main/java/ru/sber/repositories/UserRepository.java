package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.User;

import java.util.Optional;

/**
 * Хранилище с данными о пользователях
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Ищет пользователя по логину и паролю
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return Optional<User>
     */
    Optional<User> findUserByUsernameAndPassword(String login, String password);

    /**
     * Ищет пользователя по никнейм
     *
     * @param username никнейм пользователя
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверяет есть ли пользователь по логину и паролю
     *
     * @param username никнейм пользователя
     * @return Boolean
     */
    Boolean existsByUsername(String username);

    /**
     * Проверяет есть ли пользователь по логину и паролю
     *
     * @param email email пользователя
     * @return Boolean
     */
    Boolean existsByEmail(String email);
}
