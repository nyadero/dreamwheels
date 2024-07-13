package com.dreamwheels.dreamwheels.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;

    private String name;

    private String category;

    private double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date createdAt;

    private String imageUrlName;

}
