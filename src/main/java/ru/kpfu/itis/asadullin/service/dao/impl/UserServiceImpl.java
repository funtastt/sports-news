package ru.kpfu.itis.asadullin.service.dao.impl;

import ru.kpfu.itis.asadullin.model.User;
import ru.kpfu.itis.asadullin.model.UserDto;
import ru.kpfu.itis.asadullin.service.dao.Dao;
import ru.kpfu.itis.asadullin.service.dao.PasswordUtil;
import ru.kpfu.itis.asadullin.service.dao.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private Dao<User> dao = new UserDaoImpl();
    @Override
    public List<UserDto> getAll() {
        return dao.getAll().stream().map(
                u -> new UserDto(u.getFirstName(), u.getLastName())
        ).collect(Collectors.toList());
    }

    @Override
    public UserDto get(int id) {
        User user = dao.get(id);
        return new UserDto(user.getFirstName(), user.getLastName());
    }

    @Override
    public void insert(User user) {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        dao.insert(user);
    }

    @Override
    public void update(User user) {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        dao.update(user);
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }
}
