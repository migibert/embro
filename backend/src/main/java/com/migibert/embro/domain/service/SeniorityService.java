package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Seniority;
import com.migibert.embro.domain.port.SeniorityPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class SeniorityService {
    private final SeniorityPort port;

    public Seniority save(Seniority seniority) {
        return this.port.save(seniority);
    }

    public void delete(UUID seniorityId) {
        this.port.delete(seniorityId);
    }

    public Seniority findById(UUID seniorityId) {
        return this.port.findById(seniorityId);
    }

    public List<Seniority> findAll() {
        return this.port.findAll();
    }
}
