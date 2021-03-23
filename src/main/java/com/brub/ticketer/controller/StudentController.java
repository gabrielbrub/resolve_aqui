package com.brub.ticketer.controller;

import com.brub.ticketer.model.*;
import com.brub.ticketer.repository.StudentRepository;
import com.brub.ticketer.repository.RoleRepository;
import com.brub.ticketer.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("form_student")
    public String register(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "form_student";
    }


    @PostMapping("new")
    public String registerStudent(@Valid Student student, BindingResult result){
        if(result.hasErrors()) {
            return "form_student";
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setEnabled(true);
        Role userRole = roleRepository.findByName("ROLE_USER");
        student.setRoles(Arrays.asList(userRole));
        studentRepository.save(student);
        return "redirect:/student/login";
    }


    @GetMapping("dashboard")
    public String dashboard(Model model){
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> tickets = ticketRepository.findByStudentId(student.getId());
        Collections.reverse(tickets);
        model.addAttribute("tickets", tickets);
        model.addAttribute("registration", student.getRegistration());
        model.addAttribute("nome", student.getUsername());
        return "dashboard_student";
    }

    @GetMapping("/dashboard/{status}")
    public String dashboardByStatus(@PathVariable("status") String status, Model model) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> tickets = ticketRepository.findByStatusAndStudentId(Status.valueOf(status.toUpperCase()), student.getId());
        Collections.reverse(tickets);
        model.addAttribute("tickets", tickets);
        model.addAttribute("status", status.toUpperCase());
        model.addAttribute("registration", student.getRegistration());
        model.addAttribute("nome", student.getUsername());
        return "dashboard_student";
    }

}
