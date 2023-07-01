package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entity.PromoCode;
import ru.sber.repository.PromoCodeRepository;

import java.util.Optional;

@Slf4j
@Service
public class PromoCodeService implements PromoCodeInterfaceService {
    private final PromoCodeRepository productRepository;

    @Autowired
    public PromoCodeService(PromoCodeRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long addPromoCode(PromoCode promoCode) {
        log.info("Добавляет промокод c id {}", promoCode.getId());

        return productRepository.save(promoCode).getId();
    }

    @Override
    public Optional<PromoCode> getPromoCodeById(long id) {
        log.info("Получает промокод по id {}", id);

        return productRepository.findById(id);
    }

    @Override
    public boolean update(PromoCode promoCode) {
        log.info("Обновляет промокод {}", promoCode);

        productRepository.save(promoCode);

        return true;
    }

    @Override
    public boolean deletePromoCodeById(long id) {
        log.info("Удаляет промокод по id {}", id);

        productRepository.deleteById(id);

        return true;
    }

    @Override
    public boolean isPromoCode(long id) {
        log.info("Проверяет есть ли промокод с id {}", id);

        return productRepository.existsById(id);
    }
}
