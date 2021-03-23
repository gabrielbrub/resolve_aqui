package com.brub.ticketer.repository;

import com.brub.ticketer.model.Privilege;
import com.brub.ticketer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
