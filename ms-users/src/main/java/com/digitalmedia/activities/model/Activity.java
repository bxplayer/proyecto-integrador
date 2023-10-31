package com.digitalmedia.activities.model;

import com.digitalmedia.categories.model.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String information;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    private String address;
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
