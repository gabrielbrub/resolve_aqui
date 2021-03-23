package com.brub.ticketer;

import com.brub.ticketer.model.*;
import com.brub.ticketer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class DataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
          = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
          readPrivilege, writePrivilege);
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        Role userRole = createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        createUsersIfNotFound();

        alreadySetup = true;
    }

    @Transactional
    public void createUsersIfNotFound() {
        if(agentRepository.findAll().size()==0) {
            Role userRole = roleRepository.findByName("ROLE_ADMIN");
            Agent agent = new Agent("Edson", encoder.encode("12345"), "ead@ra.com", Sector.EAD);
            agent.setRoles(Arrays.asList(userRole));
            agentRepository.save(agent);
            agent = new Agent("Edna", encoder.encode("12345"), "tesouraria@ra.com", Sector.FINANCEIRO);
            agent.setRoles(Arrays.asList(userRole));
            agentRepository.save(agent);
            agent = new Agent("Edilson", encoder.encode("12345"), "secretaria@ra.com", Sector.SECRETARIA);
            agent.setRoles(Arrays.asList(userRole));
            agentRepository.save(agent);
        }
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}