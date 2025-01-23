package dev.shreya.productservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseModel{
    private String title;
    private String description;
    private double price;
    private String imageUrl;
//    @ManyToOne(cascade = {CascadeType.PERSIST})
//    private Category category;

    @ManyToOne(cascade = {CascadeType.ALL})//to update child along with parent
    private Category category;

    private int quantity;
}