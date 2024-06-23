package ru.netology.Diplom2.repository;

import org.springframework.data.repository.CrudRepository;
import ru.netology.Diplom2.entity.File;
import ru.netology.Diplom2.entity.User;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends CrudRepository<File, Integer> {
    void deleteByName(String name);

    Optional<File> findByNameAndUser(String name, User user);

    List<File> findAllByUser (User user);
}
