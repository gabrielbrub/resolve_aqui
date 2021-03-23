package com.brub.ticketer.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;
    @ManyToOne
    @JoinColumn(name="agent_id")
    private Agent agent;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String subject;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;
    @OneToMany(mappedBy = "ticket")
    private List<Message> messages;
    @Enumerated(EnumType.STRING)
    private Sector sector;

    public void addMessage(Message msg){
        messages.add(msg);
    }

    public Ticket(Student student){
        this.creationDate = LocalDateTime.now();
        this.status = Status.ABERTO;
        this.student = student;
    }

    public Ticket() {
        this.creationDate = LocalDateTime.now();
        this.status = Status.ABERTO;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime dataEncerramento) {
        this.closingDate = dataEncerramento;
    }
}
