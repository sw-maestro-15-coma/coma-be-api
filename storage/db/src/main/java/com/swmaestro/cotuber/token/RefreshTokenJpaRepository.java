package com.swmaestro.cotuber.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {
    @Query("""
            select case when count(rt) > 0 then true else false end
            from RefreshTokenEntity rt
            where rt.userId = :userId
            and rt.token = :token
            and rt.expiresAt > :now
            """
    )
    boolean isRefreshTokenExists(long userId, String token, LocalDateTime now);
}
