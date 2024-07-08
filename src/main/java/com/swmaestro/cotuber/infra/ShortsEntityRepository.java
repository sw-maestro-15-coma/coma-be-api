package com.swmaestro.cotuber.infra;

import com.swmaestro.cotuber.domain.shorts.ShortsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortsEntityRepository extends JpaRepository<ShortsEntity, Long> {

}
