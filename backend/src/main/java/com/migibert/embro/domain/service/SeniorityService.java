package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Seniority;
import com.migibert.embro.domain.port.SeniorityPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class SeniorityService {
    private final SeniorityPort port;

    public Seniority save(Seniority seniority) {
        return this.port.save(seniority);
    }

    public void delete(UUID seniorityId) {
        this.port.deleteById(seniorityId);
    }

    public Optional<Seniority> findById(UUID seniorityId) {
        return this.port.findById(seniorityId);
    }

    public Iterable<Seniority> findAll() {
        return this.port.findAll();
    }
}
