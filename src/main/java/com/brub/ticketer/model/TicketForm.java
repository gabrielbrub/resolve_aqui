package com.brub.ticketer.model;

import javax.validation.constraints.NotBlank;

public class TicketForm {
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
    private String sector;

    public TicketForm(){}

    public String getSubject() {
        return subject;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
