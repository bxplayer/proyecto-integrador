package com.integrador.evently.booking.dto;

import com.integrador.evently.activities.dto.ActivityDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingDTO {
    private Long id;
    private Long userId;
    private List<ActivityDTO> activities;
    private Date startDateTime;
    private Date endDateTime;

}
