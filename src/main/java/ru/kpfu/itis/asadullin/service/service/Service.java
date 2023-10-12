package ru.kpfu.itis.asadullin.service.service;

import java.util.List;

public interface Service<T, K> {
    List<K> getAll();

    K getById(T t);

    void insert(T t);

    void update(T t);

    void delete(T t);
}
