package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entity.PromoCode;
import ru.sber.repository.PromoCodeRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PromoCodeService implements PromoCodeInterfaceService {
    private final PromoCodeRepository promoCodeRepository;

    @Autowired
    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    @Override
    public long addPromoCode(PromoCode promoCode) {
        log.info("PromoCodeService добавляет промокод c id {}", promoCode.getId());

        return promoCodeRepository.save(promoCode).getId();
    }

    @Override
    public Optional<PromoCode> getPromoCodeById(long id) {
        log.info("PromoCodeService получает промокод по id {}", id);

        return promoCodeRepository.findById(id);
    }

    @Override
    public boolean update(PromoCode promoCode) {
        log.info("PromoCodeService обновляет промокод {}", promoCode);

        promoCodeRepository.save(promoCode);
        return true;
    }

    @Override
    public boolean deletePromoCodeById(long id) {
        log.info("PromoCodeService удаляет промокод по id {}", id);

        promoCodeRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean isPromoCodeById(long id) {
        log.info("PromoCodeService проверяет есть ли промокод с id {}", id);

        return promoCodeRepository.existsById(id);
    }

    @Override
    public List<PromoCode> getPromoCodeByDiscountRange(double minDiscount, double maxDiscount) {
        log.info("PromoCodeService получает список промокодов в диапазоне от {} до {}", minDiscount, maxDiscount);

        return promoCodeRepository.findByDiscountBetween(minDiscount, maxDiscount);
    }
}
