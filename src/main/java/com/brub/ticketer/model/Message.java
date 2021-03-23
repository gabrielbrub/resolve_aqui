package com.brub.ticketer.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private com.brub.ticketer.model.User author;
    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Ticket ticket;
    @NotBlank
    private String text;
    private LocalDateTime sendDate;


    public Message(User author, Ticket ticket, String text) {
        this.author = author;
        this.ticket = ticket;
        this.text = text;
    }

    public Message(String text) {
        this.sendDate = LocalDateTime.now();
        this.text = text;
    }

    public Message(){

    }

    public long getId() {
        return id;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
