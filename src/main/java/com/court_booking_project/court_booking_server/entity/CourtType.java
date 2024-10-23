package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table( name = "court_types")
public class CourtType {

    @Id
    @UuidGenerator
    private String id;
    private String courtTypeName;
    private int isDisabled;

    public CourtType() {

    }

    public CourtType(String courtTypeName, int isDisabled) {
        this.id = UUID.randomUUID().toString();
        this.courtTypeName = courtTypeName;
        this.isDisabled = isDisabled;
    }

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtTypeName() {
        return courtTypeName;
    }

    public void setCourtTypeName(String courtTypeName) {
        this.courtTypeName = courtTypeName;
    }
}