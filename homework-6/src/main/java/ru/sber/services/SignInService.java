package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с входом и регистрацией пользователей
 */
@Service
public class SignInService implements SignInInterfaceService {
    private final int MAXLOGHISTORY = 4;
    private List<User> users = new ArrayList<>();

    @Override
    public List<User> addUser(User user) {
        if (users.size() < MAXLOGHISTORY) {
            users.add(0, user);
            return users;
        }
        var list = new ArrayList<User>();
        list.add(user);
        list.add(users.get(0));
        list.add(users.get(1));
        return list;
    }

    @Override
    public boolean isUser(User user) {
        if (users.size() < MAXLOGHISTORY) {
            users.add(0, user);
            return true;
        }
        var list = new ArrayList<User>();
        list.add(user);
        list.add(users.get(0));
        list.add(users.get(1));
        users = list;
        return true;
    }

    @Override
    public List<User> signOutUser(String login) {
        var optionalUser = users.stream().filter(user1 -> user1.name().equals(login)).findFirst();
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var newUser = new User(user.name(), user.email(), user.password(), "Sign out");
            if (users.size() < MAXLOGHISTORY) {
                users.add(0, newUser);
                return users;
            }
            var list = new ArrayList<User>();
            list.add(newUser);
            list.add(users.get(0));
            list.add(users.get(1));
            return list;
        }
        return users;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
