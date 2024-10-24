package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Data // generates getters, setters, toString(), equals(), and hashCode() methods
@Entity // a JPA entity will be mapped to a database table.
@Builder // generates a builder pattern for the class Ex: CourtType.builder().amount(1000).status("PAID").customer(customerObject).build();
@Table( name = "court_types")
@NoArgsConstructor // generates a no-arguments constructor
@AllArgsConstructor // generates a constructor that includes all fields as parameters
@FieldDefaults(level = AccessLevel.PRIVATE) // sets all fields in the class to have private access level
public class CourtType {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "court_type_name", nullable = false)
    String courtTypeName;

    @Column(name = "is_disabled", nullable = false)
    @Builder.Default
    int isDisabled = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courtType")
    List<Court> courts;
}