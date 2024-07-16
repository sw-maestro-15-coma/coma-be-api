package com.swmaestro.cotuber.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserReader {
    private final UserRepository userRepository;

    public Optional<User> findByOAuthId(String oAuthId) {
        return userRepository.findByOAuthId(oAuthId);
    }
}
