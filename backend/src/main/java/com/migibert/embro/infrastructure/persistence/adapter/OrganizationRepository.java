package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.infrastructure.persistence.model.OrganizationTable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrganizationRepository extends CrudRepository<OrganizationTable, UUID> {
}
