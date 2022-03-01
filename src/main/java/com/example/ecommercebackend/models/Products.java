package com.example.ecommercebackend.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="products")
public class Products {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double price;
    @Column
    private String imageURL;
    @Column
    private int stock;
    @Column
    private Date date_added;

//    @ManyToOne
//    private Category category;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getDate_added() {
        return date_added;
    }

    //    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
}

