package com.company.enroller.controllers;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RunWith(SpringRunner.class)
@WebMvcTest(MeetingRestController.class)
@WithMockUser(username = "test")
public class MeetingRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ParticipantService participantService;
	
	@MockBean
	private MeetingService meetingService;

	@Test
	public void getAll() throws Exception {
		// create test participant
		Meeting meeting = new Meeting();
		meeting.setTitle("some title");
		meeting.setDescription("some desc");
		meeting.setDate("some date");

		// configure mock of ParticipantService
		Collection<Meeting> expectedMeetings = singletonList(meeting);
		given(meetingService.findMeetings("", "", null, "")).willReturn(expectedMeetings);

		// perform test using mock
		mvc.perform(get("/api/meetings").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].title", is(meeting.getTitle())));
	}

}
