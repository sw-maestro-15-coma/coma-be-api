package com.swmaestro.cotuber.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserEditor {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
