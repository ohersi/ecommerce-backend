package com.example.ecommercebackend.models;

import javax.persistence.*;

@Entity
@Table(name="users")
public class Users {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String email;
//    @Column
//    private String datecreated;
    @Column
    private String password;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getDatecreated() { return datecreated; }

    public String getPassword() {return password; }

    public void setPassword(String password) { this.password = password; }
}
