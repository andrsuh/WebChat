package ru.andrey.DAOs.DAOInterfaces;

import ru.andrey.Domain.User;

import java.util.List;


public interface UserDAO {
    User getUserByLogin(String login);

    void addUser(User user);

    List<User> allFriends(String login);
}
