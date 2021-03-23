package com.brub.ticketer.repository;

import static org.junit.Assert.assertEquals;

import com.brub.ticketer.model.Agent;
import com.brub.ticketer.model.Student;
import com.brub.ticketer.model.Sector;
import com.brub.ticketer.model.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@RunWith(SpringRunner.class)
public class RepositoryIntegrationTest {
	
	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	TicketRepository ticketRepository;
	
	@Test
	public void ifNewProjectSaved_thenSuccess() {
		Student newStudent = new Student("Teste", "12345", "student@email.com", "2015454");
		studentRepository.save(newStudent);
		assertEquals(true, studentRepository.findAll().contains(newStudent));
	}

	@Test
	public void ifNewAgentSaved_thenSuccess() {
		Agent newAgent = new Agent("Teste", "12345", "agent@email.com", Sector.EAD);
		agentRepository.save(newAgent);
		assertEquals(true, agentRepository.findAll().contains(newAgent));
	}

	@Test
	public void ifTicketisSaved_thenSuccess() {
		Student student = new Student("Teste", "12345", "student2@email.com", "2015454");
		studentRepository.save(student);
		Ticket ticket = new Ticket(student);
		ticketRepository.save(ticket);
		assertEquals(true, ticketRepository.findAll().size()==1);
	}
}