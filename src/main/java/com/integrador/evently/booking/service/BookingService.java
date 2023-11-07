package com.integrador.evently.booking.service;

import com.integrador.evently.booking.dto.BookingDTO;
import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.repository.BookingRepository;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository , UserRepository userRepository , ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookingDTO> getUserBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

    public ResponseEntity<BookingDTO> createBooking(Booking booking) throws Exception {
        User userOptional = userRepository.findById(booking.getUserId());
       if (userOptional != null) {
            bookingRepository.save(booking);
        } else {
            throw new Exception("User not found");
       }
        return null;
    }


}
