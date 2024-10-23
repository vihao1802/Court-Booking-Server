package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @UuidGenerator
    private String id;
    private float rating;
    private String comment;
    private Date reviewDate;

    @OneToOne
    @JoinColumn(name = "reservationId",referencedColumnName = "id",nullable = false)
    private Reservation reservation;

    public Review() {}

    public Review(float rating, String comment, Date reviewDate, Reservation reservation) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.reservation = reservation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
