package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.port.OrganizationPort;
import com.migibert.embro.infrastructure.persistence.model.OrganizationTable;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Component
public class OrganizationAdapter implements OrganizationPort {
    private OrganizationRepository repository;
    private JdbcAggregateTemplate template;

    @Override
    public Organization save(Organization organization) {
        Optional<OrganizationTable> existing = repository.findById(organization.id());
        OrganizationTable toSave = toPersistenceModel(organization);
        if(existing.isEmpty()) {
            template.insert(toSave);
        } else {
            template.update(toSave);
        }
        return organization;
    }

    @Override
    public void deleteById(UUID organizationId) {
        repository.deleteById(organizationId);
    }

    @Override
    public Optional<Organization> findById(UUID organizationId) {
        Optional<OrganizationTable> found = repository.findById(organizationId);
        if(found.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toDomainModel(found.get()));
    }

    @Override
    public List<Organization> findAll() {
        Iterable<OrganizationTable> found = repository.findAll();
        return StreamSupport.stream(found.spliterator(), false)
                            .map(this::toDomainModel)
                            .collect(Collectors.toList());
    }

    private OrganizationTable toPersistenceModel(Organization organization) {
        return new OrganizationTable(organization.id(), organization.name());
    }

    private Organization toDomainModel(OrganizationTable table) {
        return new Organization(table.getId(), table.getName());
    }
}
