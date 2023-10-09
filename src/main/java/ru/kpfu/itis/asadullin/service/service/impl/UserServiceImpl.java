package ru.kpfu.itis.asadullin.service.service.impl;

import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.dto.UserDto;
import ru.kpfu.itis.asadullin.service.service.Service;
import ru.kpfu.itis.asadullin.service.util.PasswordUtil;
import ru.kpfu.itis.asadullin.service.dao.Dao;
import ru.kpfu.itis.asadullin.service.dao.impl.UserDaoImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements Service<User, UserDto> {
    private final UserDaoImpl dao = new UserDaoImpl();
    @Override
    public List<UserDto> getAll() {
        return dao.getAll().stream().map(
                u -> new UserDto(u.getFirstName(), u.getLastName())
        ).collect(Collectors.toList());
    }

    @Override
    public UserDto get(User user) {
        return new UserDto(user.getFirstName(), user.getLastName());
    }

    @Override
    public void insert(User user) {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        dao.insert(user);
    }

    @Override
    public void update(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(PasswordUtil.encrypt(user.getPassword()));
            dao.updatePassword(user);
        }
        if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) {
            dao.updateProfilePicture(user);
        }
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            dao.updateMainInfo(user);
        }
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }
}
