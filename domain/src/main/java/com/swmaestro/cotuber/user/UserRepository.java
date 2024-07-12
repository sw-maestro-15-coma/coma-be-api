package com.swmaestro.cotuber.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);

    Optional<User> findByOAuthId(String oAuthId);
}
