package ru.kpfu.itis.asadullin.service.dao;

import ru.kpfu.itis.asadullin.model.User;
import ru.kpfu.itis.asadullin.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto get(int id);

    void insert(User user);

    void update(User user);

    void delete(User user);
}
