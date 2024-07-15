package com.swmaestro.cotuber.shorts.shortsEditPoint;

import com.swmaestro.cotuber.shorts.edit.ShortsEditPoint;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class ShortsEditPointRepositoryImpl implements ShortsEditPointRepository {
    private final ShortsEditPointEntityRepository repository;

    @Override
    public ShortsEditPoint save(ShortsEditPoint editPoint) {
        return repository.save(ShortsEditPointEntity.from(editPoint)).toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ShortsEditPoint> findById(long shortsEditPointId) {
        return repository.findById(shortsEditPointId).map(ShortsEditPointEntity::toDomain);
    }
}
