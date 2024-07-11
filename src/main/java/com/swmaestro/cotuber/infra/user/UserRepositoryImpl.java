package com.swmaestro.cotuber.infra.user;

import com.swmaestro.cotuber.domain.user.User;
import com.swmaestro.cotuber.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserEntityRepository userRepository;

    @Transactional
    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        return userRepository.save(entity).toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByOAuthId(String oAuthId) {
        return userRepository.findByOAuthId(oAuthId)
                .map(UserEntity::toDomain);
    }
}
