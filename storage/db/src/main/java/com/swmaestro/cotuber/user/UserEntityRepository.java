package com.swmaestro.cotuber.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
                        SELECT u
                        FROM UserEntity u
                        WHERE u.oAuthId = :oAuthId
            """)
    Optional<UserEntity> findByOAuthId(String oAuthId);
}
