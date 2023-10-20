package ru.kpfu.itis.asadullin.model.service.impl;

import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.dto.UserDto;
import ru.kpfu.itis.asadullin.model.service.Service;
import ru.kpfu.itis.asadullin.controller.util.PasswordUtil;
import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements Service<User, UserDto> {
    private final UserDaoImpl dao = new UserDaoImpl();

    @Override
    public List<UserDto> getAll() {
        List<UserDto> list = new ArrayList<>();
        for (User user : dao.getAll()) {
            UserDto userDto = new UserDto(user);
            list.add(userDto);
        }
        return list;
    }

    @Override
    public UserDto getById(int id) {
        User user = dao.getById(id);
        return new UserDto(user);
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

    public boolean ifUserExists(User user) {
        List<User> allUsers = dao.getAll();

        for (User u : allUsers) {
            if (u.getUsername().trim().equals(user.getUsername().trim()) ||
                    u.getEmail().trim().equals(user.getEmail().trim())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }
}
