package com.brub.ticketer.repository;

import com.brub.ticketer.model.Sector;
import com.brub.ticketer.model.Status;
import com.brub.ticketer.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByAgentNullAndSector(Sector sector);
    List<Ticket> findByStudentId(long studentId);
    List<Ticket> findByStatusAndStudentId(Status status, Long id);
    List<Ticket> findByStatusAndAgentIdAndSector(Status status, Long id, Sector sector);
    List<Ticket> findByAgentIdAndSector(long id, Sector sector);
}
