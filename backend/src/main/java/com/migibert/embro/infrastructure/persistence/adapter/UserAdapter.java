package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Invitation;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.User;
import com.migibert.embro.domain.port.UserPort;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.INVITATION;
import static com.migibert.embro.infrastructure.persistence.model.Tables.USER_ORGANIZATION;

@Component
public class UserAdapter implements UserPort {

    @Value("${app.oauth2.userinfo.endpoint}")
    private String userInfoEndpoint;

    private RestTemplate template;

    private DSLContext context;
    public UserAdapter(DSLContext context, RestTemplate template) {
        this.context = context;
        this.template = template;
    }

    @Override
    public void addRole(String userId, String email, UUID organizationId, Role role) {
        this.context
            .insertInto(USER_ORGANIZATION)
            .columns(USER_ORGANIZATION.USER_ID, USER_ORGANIZATION.USER_EMAIL, USER_ORGANIZATION.ORGANIZATION_ID, USER_ORGANIZATION.ROLE)
            .values(userId, email, organizationId, com.migibert.embro.infrastructure.persistence.model.enums.Role.valueOf(role.name()))
            .execute();
    }

    @Override
    public boolean hasRoleIn(String userId, UUID organizationId, Role[] roles) {
        return
            this.context
                .selectFrom(USER_ORGANIZATION)
                .where(USER_ORGANIZATION.ORGANIZATION_ID.eq(organizationId))
                .and(USER_ORGANIZATION.USER_ID.eq(userId))
                .and(USER_ORGANIZATION.ROLE.in(
                    Arrays.stream(roles)
                          .map(r -> com.migibert.embro.infrastructure.persistence.model.enums.Role.valueOf(r.name()))
                          .collect(Collectors.toList())
                ))
                .fetch()
                .collect(Collectors.toList())
                .size() > 0;
    }

    @Override
    public List<UUID> getOrganizationIds(String userId) {
        return this.context
                .selectFrom(USER_ORGANIZATION)
                .where(USER_ORGANIZATION.USER_ID.eq(userId))
                .fetch(USER_ORGANIZATION.ORGANIZATION_ID);
    }

    public void createInvitation(Invitation invitation) {
        this.context
            .insertInto(INVITATION)
            .columns(INVITATION.ID, INVITATION.ORGANIZATION_ID, INVITATION.INVITED_BY, INVITATION.ROLE, INVITATION.EMAIL)
            .values(invitation.id(), invitation.organizationId(), invitation.invitedBy(), com.migibert.embro.infrastructure.persistence.model.enums.Role.valueOf(invitation.role().name()), invitation.email())
            .execute();
    }

    public void deleteInvitation(UUID id) {
        this.context
            .deleteFrom(INVITATION)
            .where(INVITATION.ID.eq(id))
            .execute();
    }

    @Override
    public Optional<Invitation> findInvitationById(UUID id) {
        return
            this.context
                .selectFrom(INVITATION)
                .where(INVITATION.ID.eq(id))
                .fetchOptional()
                .map(r -> new Invitation(
                   r.getId(),
                   r.getInvitedBy(),
                   r.getEmail(),
                   r.getOrganizationId(),
                   Role.valueOf(r.getRole().getLiteral())
                ));
    }

    @Override
    public String findUserEmail(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, String>> result = template.exchange(userInfoEndpoint, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});
        Map<String, String> body = result.getBody();
        return body.get("email");
    }

    @Override
    public List<User> getUsersByOrganizationId(UUID organizationId) {
        return
            this.context
                .selectFrom(USER_ORGANIZATION)
                .where(USER_ORGANIZATION.ORGANIZATION_ID.eq(organizationId))
                .fetch()
                .map(r -> new User(r.getUserEmail(), Role.valueOf(r.getRole().getLiteral())))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(UUID organizationId, User user) {
        this.context
            .update(USER_ORGANIZATION)
            .set(USER_ORGANIZATION.ROLE, com.migibert.embro.infrastructure.persistence.model.enums.Role.valueOf(user.role().name()))
            .where(USER_ORGANIZATION.USER_EMAIL.eq(user.email()))
            .and(USER_ORGANIZATION.ORGANIZATION_ID.eq(organizationId))
            .execute();
    }
}
