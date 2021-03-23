package com.brub.ticketer;

import com.brub.ticketer.repository.AgentRepository;
import com.brub.ticketer.repository.StudentRepository;
import com.brub.ticketer.repository.RoleRepository;
import com.brub.ticketer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service("boi")
@ActiveProfiles("test")
class TicketerApplicationTests {

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Test
	void contextLoads() {

	}

}
