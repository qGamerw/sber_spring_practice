package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.PromoCode;

/**
 * Интерфейс для взаимодействия с промокодом
 */
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    boolean existsById(long id);
}
