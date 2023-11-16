package com.integrador.evently.booking.service;

import com.integrador.evently.booking.dto.BookingDTO;
import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.repository.BookingRepository;
import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Booking createBooking(BookingDTO booking) throws Exception {
        User userOptional = userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        Optional<List<ProductDTO>> productsInConflict = checkAvailability(booking);

        if(productsInConflict.isPresent()){
            throw new Exception("The following products are already booked" + productsInConflict);
        }

        Booking bookingEntity = modelMapper.map(booking, Booking.class);
        return bookingRepository.save(bookingEntity);
    }

    public Optional<List<BookingDTO>> getBookingsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime){
        List<BookingDTO> bookings = bookingRepository.findAll().stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class)).toList();

        bookings.forEach(booking ->  {
            if (booking.getEndDateTime().isBefore(startDateTime)){
                bookings.remove(booking);
            }else if (booking.getStartDateTime().isAfter(endDateTime)){
                bookings.remove(booking);
            };
        });

        if (!bookings.isEmpty()) {
            return Optional.of(bookings);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<ProductDTO>> checkAvailability(BookingDTO booking){
        Optional<List<BookingDTO>> bookings = getBookingsByDateRange(booking.getStartDateTime(), booking.getEndDateTime());

        if (bookings.isEmpty()){
            return Optional.empty();
        }

        List<ProductDTO> productsInConflict = new java.util.ArrayList<>();
        bookings.get().forEach(existingBooking -> {
            existingBooking.getProducts().forEach(product -> {
                    if(booking.getProducts().contains(product)){
                        productsInConflict.add(product);
                    }
            });
        });

        if (!productsInConflict.isEmpty()) {
            return Optional.of(productsInConflict);
        }else {
            return Optional.empty();
        }
    }

}
