package com.digitalmedia.photo.dto;

import lombok.Data;

@Data
public class PhotoDTO {
    private Long id;
    private String url;
    private boolean isMain;
    private Long activityId;
}
