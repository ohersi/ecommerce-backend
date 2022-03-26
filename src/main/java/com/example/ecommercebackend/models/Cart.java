package com.example.ecommercebackend.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "products_id")
    private Products products_id;
    @OneToOne
    @JoinColumn(name= "users_id")
    Users users;
    @Column(nullable = false)
    private int quantity;
    @Column(name = "date_added")
    @CreationTimestamp
    private Timestamp dateAdded;
    @Column (name= "date_updated")
    @UpdateTimestamp
    private Timestamp dateUpdated;

    public int getId() {
        return id;
    }

    public Products getProducts_id() {
        return products_id;
    }

    public void setProduct_id(Products products_id) {
        this.products_id = products_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users_id) {
        this.users = users_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp date_added) {
        this.dateAdded = date_added;
    }

    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp date_updated) {
        this.dateUpdated = date_updated;
    }
}
