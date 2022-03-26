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
    @JoinColumn(name="products_id")
    private Products products_id;
    @OneToOne
    @JoinColumn(name= "users_id")
    Users users_id;
    @Column
    private int quantity;
    @Column
    @CreationTimestamp
    private Timestamp date_added;
    @Column
    @UpdateTimestamp
    private Timestamp date_updated;

    public int getId() {
        return id;
    }

    public Products getProducts_id() {
        return products_id;
    }

    public void setProduct_id(Products products_id) {
        this.products_id = products_id;
    }

    public Users getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Users users_id) {
        this.users_id = users_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDate_added() {
        return date_added;
    }

    public void setDate_added(Timestamp date_added) {
        this.date_added = date_added;
    }

    public Timestamp getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(Timestamp date_updated) {
        this.date_updated = date_updated;
    }
}
