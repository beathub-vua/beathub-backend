package xyz.beathub.api.repository;

import org.springframework.stereotype.Repository;
import xyz.beathub.api.model.User;

import java.util.List;

@Repository
public interface UserRepository {

    List<User> getUsers();

}
