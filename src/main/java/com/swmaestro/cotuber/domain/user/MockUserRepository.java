package com.swmaestro.cotuber.domain.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class MockUserRepository implements UserRepository {
    private final Map<Long, User> repository = new HashMap<>();

    @Override
    public User save(User user) {
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findByOAuthId(String oAuthId) {
        return filterBy(User::getOAuthId, oAuthId);
    }
    
    private Optional<User> filterBy(Function<User, String> function, String key) {
        return repository.values().stream()
                .filter(it -> function.apply(it).equals(key))
                .findAny();
    }
}
