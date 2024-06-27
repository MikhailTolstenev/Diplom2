package ru.netology.Diplom2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.netology.Diplom2.entity.User;


import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional <User> findByLogin(String login);
}