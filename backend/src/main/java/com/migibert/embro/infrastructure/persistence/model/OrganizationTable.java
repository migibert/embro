package com.migibert.embro.infrastructure.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("ORGANIZATION")
public class OrganizationTable {
    @Id
    private UUID id;
    private String name;
}