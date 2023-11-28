package com.integrador.evently.providers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.integrador.evently.categories.model.Category;
import com.integrador.evently.products.model.Product;
import com.integrador.evently.users.model.User;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String information;
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "provider_category",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> category;

    //@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    //private List<Photo> photos;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}
