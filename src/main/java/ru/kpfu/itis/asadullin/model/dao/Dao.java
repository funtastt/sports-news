package ru.kpfu.itis.asadullin.model.dao;

import ru.kpfu.itis.asadullin.model.entity.Master;

import java.util.List;

public interface Dao<T> {
    T getById(int id);
    List<T> getAll();
}
