package com.brub.ticketer.controller;


import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.brub.ticketer.AuthenticationService;
import com.brub.ticketer.model.*;
import com.brub.ticketer.repository.AgentRepository;
import com.brub.ticketer.repository.StudentRepository;
import com.brub.ticketer.repository.RoleRepository;
import com.brub.ticketer.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	
	@LocalServerPort
	private int port;


	@Autowired
	StudentRepository studentRepository;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	private MockMvc mockMvc;

	private static boolean setUpIsDone = false;


	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Before
	public void loadUsers() {
		if(!setUpIsDone) {
			Ticket ticket = new Ticket();
			ticket.setSubject("problema");
			ticket.setSector(Sector.FINANCEIRO);
			Student student = new Student("testevaldo", encoder.encode("12345"), "testevaldo@test.com", "20178955");
			Role userRole = roleRepository.findByName("ROLE_USER");
			student.setRoles(Arrays.asList(userRole));
			studentRepository.save(student);
			userRole = roleRepository.findByName("ROLE_ADMIN");
			Agent agent = new Agent("Ed", "12345", "ed@ra.com", Sector.FINANCEIRO);
			agent.setRoles(Arrays.asList(userRole));
			agent.setTickets(Arrays.asList(ticket));
			agentRepository.save(agent);
			ticket.setStudent(student);
			ticketRepository.save(ticket);
		}
		setUpIsDone = true;
	}


	@Test
	@WithUserDetails("testevaldo@test.com")
	public void studentDashboardReturnsTicketSubject_thenSuccess() throws Exception {
		this.mockMvc.perform(get("/student/dashboard"))
				.andExpect(content().string(containsString("problema")))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("dashboard_student"));
	}

	@Test
	@WithUserDetails("ed@ra.com")
	public void agentDashboardReturnsTicketSubject_thenSuccess() throws Exception {
		this.mockMvc.perform(get("/agent/dashboard/aberto"))
				.andExpect(content().string(containsString("problema")))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("dashboard_agent"));
	}

	@Test
	@WithUserDetails(value = "testevaldo@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
	public void ticketRetunsView_thenSuccess() throws Exception {
		this.mockMvc.perform(get("/ticket/1"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("ticket"));
	}
	
	@Test
	public void homePageReturnsVersionNumberCorrectly_thenSuccess() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Aluno")))
				.andExpect(content().string(containsString("Agente")))
				.andExpect(MockMvcResultMatchers.view().name("home"));
	}

}