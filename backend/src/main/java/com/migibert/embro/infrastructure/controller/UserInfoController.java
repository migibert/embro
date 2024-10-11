package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.service.OrganizationService;
import com.migibert.embro.domain.service.UserService;
import com.migibert.embro.infrastructure.controller.dto.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

@RestController
@AllArgsConstructor
public class UserInfoController {
    private UserService userService;
    private OrganizationService organizationService;

    @GetMapping("/me")
    public ResponseEntity get(Principal principal) {
        String userId = principal.getName();
        Map<UUID, Role> rolesByOrganization = userService.getRolesByOrganizationForUser(userId);
        Set<Organization> allowed = organizationService.findByIds(rolesByOrganization.keySet());
        List<UserInfo> userInfos = new ArrayList<>(allowed.size());
        for(Organization organization: allowed) {
            userInfos.add(new UserInfo(organization, rolesByOrganization.get(organization.id())));
        }
        return ResponseEntity.ok(userInfos);
    }
}
