package ru.hf.service;

import ru.hf.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User getByUsername(String username);

    User getById(Long id);

    void delete(Long id);
}
