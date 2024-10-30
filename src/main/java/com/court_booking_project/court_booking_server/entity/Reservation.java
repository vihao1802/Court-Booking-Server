package com.court_booking_project.court_booking_server.entity;

import com.court_booking_project.court_booking_server.constant.ReservationState;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

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

    @Column(name = "check_in_date",nullable = false)
    Date checkInDate;

    @Column(name = "check_out_date", nullable = false)
    Date checkOutDate;

    @Column(name = "total_price",nullable = false)
    float totalPrice;

    @Column(name = "reservation_date",nullable = false)
    Date reservationDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reservation_state",nullable = false)
    ReservationState reservationState = ReservationState.PENDING;

    @Column(name = "created_at")
    Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courtId", referencedColumnName = "id", nullable = false)
    Court court;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentMethodId",referencedColumnName = "id")
    PaymentMethod paymentMethod;

}
