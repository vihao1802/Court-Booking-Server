package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "courts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Court {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "court_name", nullable = false)
    String courtName;

    @Column(name = "court_description", nullable = false)
    String courtDescription;

    @Column(name = "court_address", nullable = false)
    String courtAddress;

    @Column(name = "rental_price_per_hour", nullable = false)
    long rentalPricePerHour;

    @Column(name = "minimum_rental_time", nullable = false)
    int minimumRentalTime;

    @Column(name = "maximum_rental_time", nullable = false)
    int maximumRentalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "court_type_id",referencedColumnName = "id", nullable = false)
    CourtType courtType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "court", cascade = CascadeType.ALL)
    List<CourtImage> courtImageList;

}
