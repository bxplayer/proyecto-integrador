package com.integrador.evently.booking.model;

import com.integrador.evently.products.model.Product;
import com.integrador.evently.providers.model.Provider;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@ToString
@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "booking_product",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_datetime")
    private LocalDate eventDate;

}