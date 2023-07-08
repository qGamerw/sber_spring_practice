package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.ERole;
import ru.sber.entities.Role;

import java.util.Optional;

/**
 * Репозиторий с ролями
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Ищет роль по названию
     *
     * @param name название роли
     * @return Optional<Role>
     */
    Optional<Role> findByName(ERole name);
}