package com.ecommerce.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import jakarta.persistence.*;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Product {
    @jakarta.persistence.Id
    @Id
    private String id;

    private String name;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private double price;
    private int stock;
}
