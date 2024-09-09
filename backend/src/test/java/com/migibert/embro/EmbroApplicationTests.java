package com.migibert.embro;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migibert.embro.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmbroApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SecurityFilterChain mocked;

	private Principal principal = () -> "test|rdm1234";

	@Autowired
	private ObjectMapper mapper;

	private Organization organization;
	private Skill java;
	private Skill decisionMaking;
	private Skill projectManagement;
	private Role softwareEngineer;
	private Seniority junior;
	private Seniority medium;
	private Seniority senior;
	private Team team;
	private Collaborator mikael;

	@BeforeAll
	void setup() throws Exception {
		organization = createOrganization();

		java = createSkill(organization, "Java");
		decisionMaking = createSkill(organization, "Decision Making");
		projectManagement = createSkill(organization, "Project Management");

		junior = createSeniority(organization, "Junior");
		medium = createSeniority(organization, "Medium");
		senior = createSeniority(organization, "Senior");

		softwareEngineer = createRole(organization, "Software Engineer");

		team = createTeam(organization, "B2C");
		mikael = create(
			"/organizations/" + organization.id() + "/collaborators/",
			new Collaborator(
				null,
				"mgibert@gmail.com",
				"Mikael",
				"Gibert",
				softwareEngineer.name(),
				LocalDate.of(1987, 2, 6),
				LocalDate.of(2023, 2, 8),
				senior.name(),
				Set.of(
					new SkillLevel(java, 5),
					new SkillLevel(projectManagement,3),
					new SkillLevel(decisionMaking, 4)
				)
			),
			Collaborator.class
		);
	}

	private Organization createOrganization() throws Exception {
		Organization organization = new Organization(null, "Organization " + UUID.randomUUID());
		return create("/organizations/", organization, Organization.class);
	}

	private Skill createSkill(Organization organization, String name) throws Exception {
		Skill skill = new Skill(null, name);
		return create("/organizations/" + organization.id() + "/skills/", skill, Skill.class);
	}

	private Seniority createSeniority(Organization organization, String name) throws Exception {
		Seniority seniority = new Seniority(null, name);
		return create("/organizations/" + organization.id() + "/seniorities/", seniority, Seniority.class);
	}

	private Role createRole(Organization organization, String name) throws Exception {
		Role role = new Role(null, name);
		return create("/organizations/" + organization.id() + "/roles/", role, Role.class);
	}
	private Team createTeam(Organization organization, String name) throws Exception {
		Team team = new Team(null, name);
		return create("/organizations/" + organization.id() + "/teams/", team, Team.class);
	}

	private <T> void expectBodyToBe(String uri, T expected, Class<T> clazz) throws Exception {
		MvcResult result = mvc.perform(get(uri).principal(principal)).andReturn();
		T found = mapper.readValue(result.getResponse().getContentAsString(), clazz);
		Assertions.assertEquals(expected, found);
	}

	private <T> T create(String uri, T value, Class<T> clazz) throws Exception {
		String json = mapper.writeValueAsString(value);
		MvcResult result = mvc.perform(post(uri).principal(principal).contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		String response = result.getResponse().getContentAsString();
		return mapper.readValue(response, clazz);
	}

	@Test
	void nominalSetup() throws Exception {
		expectBodyToBe("/organizations/" + organization.id(), organization, Organization.class);
		expectBodyToBe("/organizations/" + organization.id() + "/skills/" + java.id(), java, Skill.class);
		expectBodyToBe("/organizations/" + organization.id() + "/skills/" + decisionMaking.id(), decisionMaking, Skill.class);
		expectBodyToBe("/organizations/" + organization.id() + "/skills/" + projectManagement.id(), projectManagement, Skill.class);
		expectBodyToBe("/organizations/" + organization.id() + "/seniorities/" + junior.id(), junior, Seniority.class);
		expectBodyToBe("/organizations/" + organization.id() + "/seniorities/" + medium.id(), medium, Seniority.class);
		expectBodyToBe("/organizations/" + organization.id() + "/seniorities/" + senior.id(), senior, Seniority.class);
		expectBodyToBe("/organizations/" + organization.id() + "/roles/" + softwareEngineer.id(), softwareEngineer, Role.class);
		expectBodyToBe("/organizations/" + organization.id() + "/teams/" + team.id(), team, Team.class);
		expectBodyToBe("/organizations/" + organization.id() + "/collaborators/" + mikael.id(), mikael, Collaborator.class);
	}

	@Test
	void testMembershipManagement() throws Exception {
		String url = "/organizations/" + organization.id() + "/teams/" + team.id() + "/members/";
		mvc.perform(get(url).principal(principal)).andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
		mvc.perform(put(url + mikael.id()).principal(principal)).andExpect(status().isNoContent());
		MvcResult result = mvc.perform(get(url).principal(principal)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty()).andReturn();
		List<Collaborator> value = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
		Assertions.assertEquals(mikael, value.get(0));
		mvc.perform(delete(url + mikael.id()).principal(principal)).andExpect(status().isNoContent());
		mvc.perform(get(url).principal(principal)).andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
	}
}
