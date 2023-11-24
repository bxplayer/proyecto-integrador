package com.integrador.evently.products.repository;

import com.integrador.evently.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /*
     * select b.*
     * from booking as b
     * inner join booking_product as bp on b.id = bp.booking_id
     * inner join products as p on p.id = bp.product_id
     * where :eventDate between b.start_datetime and b.end_datetime
     */
    @Query("select p " +
            "from Product p " +
            "inner join \"BookingProduct\" bp on p.id = bp.product.id " +
            "inner join \"Booking\" b on b.id = bp.booking.id " +
            "where p.id = :productId " +
            "and :eventDate between b.startDatetime and b.endDatetime")
    List<Product> findProductAvailabilityInBookings(Long productId, LocalDate eventDate);


}
