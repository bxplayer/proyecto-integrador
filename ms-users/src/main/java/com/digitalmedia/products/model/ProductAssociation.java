package com.digitalmedia.products.model;

import com.digitalmedia.activities.model.Activity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class ProductAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "product_activity_association",
            joinColumns = @JoinColumn(name = "product_association_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;


    @ManyToMany
    @JoinTable(
            name = "product_activity_association",
            joinColumns = @JoinColumn(name = "product_association_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activities;

}
