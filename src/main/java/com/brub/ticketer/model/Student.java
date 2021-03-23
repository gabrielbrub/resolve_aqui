package com.brub.ticketer.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
public class Student extends User{
    @NotBlank
    private String registration;

    public Student() {
    }

    public Student(String username, String password, String email, String registration) {
        super(username, password, email);
        this.registration = registration;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

}
