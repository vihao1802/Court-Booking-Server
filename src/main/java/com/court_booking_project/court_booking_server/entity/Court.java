package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table( name = "courts")
public class Court {

    @Id
    @UuidGenerator
    private String id;
    private String courtName;
    private String courtDescription;
    private String courtAddress;
    private String courtMainImage;
    private String courtSubImage1;
    private String courtSubImage2;
    private String courtSubImage3;
    private String courtSubImage4;
    private long rentalPricePerHour;
    private int minimumRentalTime;
    private int maximumRentalTime;

    @ManyToOne
    @JoinColumn(name= "courtTypeId",referencedColumnName = "id",nullable = false)
    private CourtType courtType;

    public Court() {
    }

    public Court(String courtName, String courtDescription, String courtAddress, String courtMainImage, String courtSubImage1, String courtSubImage2, String courtSubImage3, String courtSubImage4, long rentalPricePerHour, int minimumRentalTime, int maximumRentalTime, CourtType courtType) {
        this.id = UUID.randomUUID().toString();
        this.courtName = courtName;
        this.courtDescription = courtDescription;
        this.courtAddress = courtAddress;
        this.courtMainImage = courtMainImage;
        this.courtSubImage1 = courtSubImage1;
        this.courtSubImage2 = courtSubImage2;
        this.courtSubImage3 = courtSubImage3;
        this.courtSubImage4 = courtSubImage4;
        this.rentalPricePerHour = rentalPricePerHour;
        this.minimumRentalTime = minimumRentalTime;
        this.maximumRentalTime = maximumRentalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCourtDescription() {
        return courtDescription;
    }

    public void setCourtDescription(String courtDescription) {
        this.courtDescription = courtDescription;
    }

    public String getCourtAddress() {
        return courtAddress;
    }

    public void setCourtAddress(String courtAddress) {
        this.courtAddress = courtAddress;
    }

    public String getCourtMainImage() {
        return courtMainImage;
    }

    public void setCourtMainImage(String courtMainImage) {
        this.courtMainImage = courtMainImage;
    }

    public String getCourtSubImage1() {
        return courtSubImage1;
    }

    public void setCourtSubImage1(String courtSubImage1) {
        this.courtSubImage1 = courtSubImage1;
    }

    public String getCourtSubImage2() {
        return courtSubImage2;
    }

    public void setCourtSubImage2(String courtSubImage2) {
        this.courtSubImage2 = courtSubImage2;
    }

    public String getCourtSubImage3() {
        return courtSubImage3;
    }

    public void setCourtSubImage3(String courtSubImage3) {
        this.courtSubImage3 = courtSubImage3;
    }

    public String getCourtSubImage4() {
        return courtSubImage4;
    }

    public void setCourtSubImage4(String courtSubImage4) {
        this.courtSubImage4 = courtSubImage4;
    }

    public long getRentalPricePerHour() {
        return rentalPricePerHour;
    }

    public void setRentalPricePerHour(long rentalPricePerHour) {
        this.rentalPricePerHour = rentalPricePerHour;
    }

    public int getMinimumRentalTime() {
        return minimumRentalTime;
    }

    public void setMinimumRentalTime(int minimumRentalTime) {
        this.minimumRentalTime = minimumRentalTime;
    }

    public int getMaximumRentalTime() {
        return maximumRentalTime;
    }

    public void setMaximumRentalTime(int maximumRentalTime) {
        this.maximumRentalTime = maximumRentalTime;
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public void setCourtType(CourtType courtType) {
        this.courtType = courtType;
    }
}
