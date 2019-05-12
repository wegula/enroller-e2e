package com.company.enroller.controllers;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantRestController.class)
@WithMockUser(username = "test")
public class ParticipantRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ParticipantService participantService;

	@Test
	public void getAll() throws Exception {
		// create test participant
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		// configure mock of ParticipantService
		Collection<Participant> expectedParticipants = singletonList(participant);
		given(participantService.getAll()).willReturn(expectedParticipants);

		// perform test using mock
		mvc.perform(get("/api/participants").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].login", is(participant.getLogin())));
	}

	@Test
	public void findByLogin() throws Exception {
		// create test participant
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		// configure mock of ParticipantService
		given(participantService.findByLogin(participant.getLogin())).willReturn(participant);

		// perform test using mock
		mvc.perform(get("/api/participants/testlogin").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("login", is("testlogin")));
	}

	@Test
	public void addParticipant() throws Exception {
		// create test participant
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		// configure mock of ParticipantService
		given(participantService.findByLogin(participant.getLogin())).willReturn(null);

		// perform test using mock
		mvc.perform(post("/api/participants").with(csrf()).characterEncoding(
                "UTF-8").contentType(MediaType.APPLICATION_JSON).content("{\"login\":\"testlogin\",\"password\":\"testpassword\"}")).andExpect(status().is(201));
	}
	
	@Test
	public void updateParticipant() throws Exception {
		// create test participant
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testPassword");

		// configure mock of ParticipantService
		given(participantService.findByLogin(participant.getLogin())).willReturn(participant);
		participant.setPassword("newpassword");
		//given(participantService.update(participant));
		
		// perform test using mock
		mvc.perform(put("/api/participants/testlogin").with(csrf()).characterEncoding(
                "UTF-8").contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"newpassword\"}")).andExpect(status().isNoContent());
	}
}
