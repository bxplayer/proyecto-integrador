package com.digitalmedia.activities.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityDTO {
    private Long id;
    private String name;
    private String information;
    private Date creationDate;
    private Date updateDate;
    private String address;
    private double price;
    private Long categoryId;
}
