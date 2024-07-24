package com.swmaestro.cotuber.log;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogRepositoryImpl implements LogRepository {
    private final LogEntityRepository repository;

    @Transactional
    @Override
    public Log save(Log log) {
        return repository.save(LogEntity.from(log)).toDomain();
    }
}
