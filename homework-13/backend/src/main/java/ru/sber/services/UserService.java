package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entity.User;
import ru.sber.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Класс для взаимодействия с клиентом
 */
@Slf4j
@Service
public class UserService implements UserInterfaceService {

    private final UserRepository userRepository;
    private final BasketInterfaceService basketInterfaceService;
    private final PromoCodeInterfaceService promoCodeInterfaceService;

    public UserService(UserRepository userRepository,
                       BasketInterfaceService basketInterfaceService,
                       PromoCodeInterfaceService promoCodeInterfaceService) {

        this.userRepository = userRepository;
        this.basketInterfaceService = basketInterfaceService;
        this.promoCodeInterfaceService = promoCodeInterfaceService;
    }

//    @Override
//    public long addUser(User user) {
//        log.info("UserService добавляет клиента с id {}", user);
//
//        User newUser = new User(0,
//                User.getName(), User.getEmail(), User.getIdCard(),
//                User.getUsername(), User.getPassword(), BigDecimal.ZERO);
//
//
//        return userRepository.save(newUser).getId();
//    }

    @Override
    public Optional<User> getUserById(long id) {
        log.info("UserService получает клиента с id {}", id);

        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public boolean deleteUserById(long id) {
        log.info("UserService удаляет клиента с id {}", id);

        userRepository.deleteById(id);

        return true;
    }

    @Override
    public BigDecimal getPrice(long idUser, long idPromoCode) {
        log.info("UserService получает сумма к оплате у клиента id {} с учетом скидки", idUser);

        var price = userRepository.findById(idUser).get().getPrice();
        var promoCode = promoCodeInterfaceService.getPromoCodeById(idPromoCode)
                .get().getDiscount();

        var discount = promoCode / 100;

        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)));
    }

    @Override
    public long getIdCard(long id) {
        log.info("UserService получает id карты у клиента id {}", id);

        return userRepository.findById(id).get().getIdCard();
    }

    @Override
    public boolean existsUserByName(User user) {
        return userRepository.existsByUsername(user.getUsername());
    }
}
