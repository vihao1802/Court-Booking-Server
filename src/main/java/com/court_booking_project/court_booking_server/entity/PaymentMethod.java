package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "payment_methods")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "payment_method_name",nullable = false)
    String paymentMethodName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentMethod")
    List<Reservation> reservations;

}
