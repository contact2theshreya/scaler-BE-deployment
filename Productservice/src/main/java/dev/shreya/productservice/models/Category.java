package dev.shreya.productservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Category extends BaseModel {
    private String title;
//    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE})
////    @ManyToMany(mappedBy = "dhqwohewhjepd")
//    @JsonIgnore
//    private List<Product> products;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    @JsonIgnore//to remove this form response
    private List<Product> products;

}