package ru.kpfu.itis.asadullin.model.service;

import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.util.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getById(int id);

    void insert(User user);

    void update(User user);

    boolean ifUserExists(User user);

    void delete(User user);
}
