package com.integrador.evently.booking.controller;

import com.integrador.evently.booking.dto.BookingDTO;
import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody Booking booking) throws Exception {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BookingDTO>> getUserBookings(@PathVariable Long userId) {
        List<BookingDTO> bookingDTOS = bookingService.getUserBookings(userId);
        return new ResponseEntity<>(bookingDTOS, HttpStatus.OK);
    }

}
