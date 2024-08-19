package com.migibert.embro;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migibert.embro.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmbroApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	private Organization organization;
	private Skill java;
	private Skill decisionMaking;
	private Skill projectManagement;
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

		team = createTeam(organization, "B2C");
		mikael = create(
			"/organizations/" + organization.id() + "/collaborators/",
			new Collaborator(
				null,
				"mgibert@gmail.com",
				"Mikael",
				"Gibert",
				"Engineering Manager",
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

	private Team createTeam(Organization organization, String name) throws Exception {
		Team team = new Team(null, name);
		return create("/organizations/" + organization.id() + "/teams/", team, Team.class);
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
		expectBodyToBe("/organizations/" + organization.id() + "/teams/" + team.id(), team, Team.class);
		expectBodyToBe("/organizations/" + organization.id() + "/collaborators/" + mikael.id(), mikael, Collaborator.class);
	}

	private <T> void expectBodyToBe(String uri, T expected, Class<T> clazz) throws Exception {
		MvcResult result = mvc.perform(get(uri)).andReturn();
		T found = mapper.readValue(result.getResponse().getContentAsString(), clazz);
		Assertions.assertEquals(expected, found);
	}

	private <T> T create(String uri, T value, Class<T> clazz) throws Exception {
		String json = mapper.writeValueAsString(value);
		MvcResult result = mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		String response = result.getResponse().getContentAsString();
		return mapper.readValue(response, clazz);
	}
}
