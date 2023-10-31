package com.digitalmedia.photo.model;

import com.digitalmedia.activities.model.Activity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private boolean isMain;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Activity activity;

}
