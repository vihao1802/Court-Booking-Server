package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "rating", nullable = false)
    float rating;

    @Column(name = "comment", nullable = false)
    String comment;

    @Column(name = "review_date", nullable = false)
    Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "reservationId",referencedColumnName = "id",nullable = false)
    Reservation reservation;
}
