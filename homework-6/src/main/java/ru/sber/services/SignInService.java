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
    private List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user){
        if (users.size() < 4){
            users.add(0, user);
            return;
        }
        var list = new ArrayList<User>();
        list.add(user);
        list.add(users.get(0));
        list.add(users.get(1));
        users = list;
    }

    @Override
    public void isUser(User user){
        if (users.size() < 4) {
            users.add(0, user);
            return;
        }
        var list = new ArrayList<User>();
        list.add(user);
        list.add(users.get(0));
        list.add(users.get(1));
        users = list;
    }

    @Override
    public void signOutUser(String login) {
        var optionalUser = users.stream().filter(user1 -> user1.name().equals(login)).findFirst();

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var newUser = new User(user.name(), user.email(), user.password(), "Sign out");
            if (users.size() < 4) {
                users.add(0, newUser);
                return;
            }
            var list = new ArrayList<User>();
            list.add(newUser);
            list.add(users.get(0));
            list.add(users.get(1));
            users = list;
        }
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
