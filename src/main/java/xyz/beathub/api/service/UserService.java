package xyz.beathub.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.beathub.api.model.User;
import xyz.beathub.api.repository.impl.UserRepositoryImpl;

import java.util.List;

@Service
public class UserService {

    private final UserRepositoryImpl repository;

    @Autowired
    public UserService(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.getUsers();
    }
}
