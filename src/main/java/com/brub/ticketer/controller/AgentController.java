package com.brub.ticketer.controller;

import com.brub.ticketer.model.*;
import com.brub.ticketer.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private TicketRepository ticketRepository;


    @GetMapping("login")
    public String login(){
        return "login_agent";
    }


    @GetMapping("dashboard")
    public String dashboard(Model model){
        Agent agent = (com.brub.ticketer.model.Agent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> tickets = ticketRepository.findByAgentIdAndSector(agent.getId(), agent.getSector());
        tickets.addAll(ticketRepository.findByAgentNullAndSector(agent.getSector()));
        Collections.reverse(tickets);
        model.addAttribute("tickets", tickets);
        model.addAttribute("nome", agent.getUsername());
        model.addAttribute("sector", agent.getSector().name());
        return "dashboard_agent";
    }

    @GetMapping("/dashboard/{status}")
    public String porStatus(@PathVariable("status") String status, Model model) {
        Agent agent = (Agent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> tickets;
        tickets = Status.valueOf(status.toUpperCase()) == Status.ABERTO ? ticketRepository.findByAgentNullAndSector(agent.getSector()) :
        ticketRepository.findByStatusAndAgentIdAndSector(Status.valueOf(status.toUpperCase()), agent.getId(), agent.getSector());
        Collections.reverse(tickets);
        model.addAttribute("tickets", tickets);
        model.addAttribute("status", status.toUpperCase());
        model.addAttribute("nome", agent.getUsername());
        model.addAttribute("sector", agent.getSector().name());
        return "dashboard_agent";
    }

}
