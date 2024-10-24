package com.court_booking_project.court_booking_server.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "court_images")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourtImage {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "court_image_src", nullable = false)
    String courtImageSrc;

    @Column(name = "image_type", nullable = false)
    String imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", referencedColumnName = "id") // nullable true
    Court court;

}
