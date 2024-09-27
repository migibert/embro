package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.model.Member;
import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.port.CollaboratorPort;
import com.migibert.embro.domain.port.TeamPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TeamService {
    private final TeamPort teamPort;
    private final CollaboratorPort collaboratorPort;

    public Team create(UUID organizationId, Team team) {
        UUID id = UUID.randomUUID();
        return this.teamPort.create(
                organizationId,
                new Team(
                        id,
                        team.name(),
                        team.mission(),
                        team.email(),
                        team.instantMessage(),
                        team.phone()
                )
        );
    }
    public Team update(UUID organizationId, Team team) {
        return this.teamPort.update(organizationId, team);
    }

    public void delete(UUID organizationId, UUID teamId) {
        this.teamPort.deleteById(organizationId, teamId);
    }

    public Optional<Team> findById(UUID organizationId, UUID teamId) {
        return this.teamPort.findById(organizationId, teamId);
    }

    public Set<Team> findAll(UUID organizationId) {
        return this.teamPort.findAll(organizationId);
    }

    public Member addMember(UUID organizationId, UUID teamId, UUID memberId, boolean keyPlayer) {
        Optional<Team> team = this.teamPort.findById(organizationId, teamId);
        Optional<Collaborator> member = this.collaboratorPort.findById(organizationId, memberId);
        if(team.isEmpty()) {
            throw new IllegalArgumentException("Team and Member must be part of the same organization");
        }
        if(member.isEmpty()) {
            throw new IllegalArgumentException("Team and Member must be part of the same organization");
        }
        return this.teamPort.addMember(organizationId, teamId, memberId, keyPlayer);
    }

    public void removeMember(UUID organizationId, UUID teamId, UUID memberId) {
        this.teamPort.removeMember(organizationId, teamId, memberId);
    }

    public Set<Member> listMembers(UUID organizationId, UUID teamId) {
        return this.teamPort.listMembers(organizationId, teamId);
    }
}
