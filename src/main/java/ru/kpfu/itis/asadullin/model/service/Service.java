package ru.kpfu.itis.asadullin.model.service;

import java.util.List;

public interface Service<T, K> {
    List<K> getAll();

    K getById(int id);

    void insert(T t);

    void update(T t);

    void delete(T t);
}
