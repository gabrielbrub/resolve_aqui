package com.brub.ticketer.model;

import javax.persistence.*;

@Entity
public class Agent extends User{
    private Sector sector;

    public Agent(){}

    public Agent(String username, String password, String email, Sector sector) {
        super(username, password, email);
        this.sector = sector;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

}
