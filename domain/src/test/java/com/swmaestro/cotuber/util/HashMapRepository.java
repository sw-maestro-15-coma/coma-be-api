package com.swmaestro.cotuber.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HashMapRepository<T> {
    private final Map<Long, T> map = new HashMap<>();

    protected void save(long id, T entity) {
        map.put(id, entity);
    }

    public Optional<T> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    public List<T> findAll() {
        return List.copyOf(map.values());
    }

    public void clear() {
        map.clear();
    }
}
