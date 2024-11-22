package com.court_booking_project.court_booking_server.entity;

import com.court_booking_project.court_booking_server.constant.PaymentMethod;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table( name = "reservations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @UuidGenerator
    @Column(name = "id",nullable = false)
    String id;

    @Column(name = "check_in_time",nullable = false)
    String checkInTime;

    @Column(name = "check_out_time", nullable = false)
    String checkOutTime;

    @Column(name = "total_price",nullable = false)
    long totalPrice;

    @Column(name = "reservation_date",nullable = false)
    LocalDate reservationDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reservation_state",nullable = false)
    @Builder.Default
    ReservationState reservationState = ReservationState.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    PaymentMethod paymentMethod;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courtId", referencedColumnName = "id", nullable = false)
    Court court;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    User user;

}
