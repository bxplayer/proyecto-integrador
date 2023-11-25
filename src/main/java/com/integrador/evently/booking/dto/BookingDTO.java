package com.integrador.evently.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data
public class BookingDTO {
    @NotNull
    private Long userId;
    @NotNull
    private LocalDate eventDate;
    private List<Long> productIds;
}
