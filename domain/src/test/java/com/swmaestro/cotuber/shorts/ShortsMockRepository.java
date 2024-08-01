package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.util.HashMapRepository;

import java.util.List;

public class ShortsMockRepository extends HashMapRepository<Shorts> implements ShortsRepository {
    @Override
    public Shorts save(Shorts shorts) {
        super.save(shorts.getId(), shorts);
        return shorts;
    }

    @Override
    public List<Shorts> findAllByUserId(long userId) {
        return super.findAll().stream()
                .filter(shorts -> shorts.getUserId() == userId)
                .toList();
    }
}
