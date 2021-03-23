package com.brub.ticketer;



import com.brub.ticketer.repository.AgentRepository;
import com.brub.ticketer.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TicketerApplication {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
    AgentRepository agentRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(TicketerApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(){
//		return args -> {
//			Role userRole = roleRepository.findByName("ROLE_ADMIN");
//			Agente agente = new Agente("Edson", encoder.encode("12345"), "edson@ra.com", Setor.EAD);
//			agente.setRoles(Arrays.asList(userRole));
//			agenteRepository.save(agente);
//			agente = new Agente("Edna", encoder.encode("12345"), "edna@ra.com", Setor.FINANCEIRO);
//			agente.setRoles(Arrays.asList(userRole));
//			agenteRepository.save(agente);
//			agente = new Agente("Edilson", encoder.encode("12345"), "edilson@ra.com", Setor.SECRETARIA);
//			agente.setRoles(Arrays.asList(userRole));
//			agenteRepository.save(agente);
//		};
//	}

}
