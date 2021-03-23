package com.brub.ticketer.repository;

import com.brub.ticketer.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Message, Long> {

}
