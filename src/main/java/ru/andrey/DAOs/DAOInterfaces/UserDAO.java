package ru.andrey.DAOs.DAOInterfaces;

import ru.andrey.Domain.User;

import java.util.List;

public interface UserDAO {
    void addUser(String username, String password);

    List<User> listUsers();
}
