package com.tarpa.tourism.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Personal Information
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String gender;

    private LocalDate dateOfBirth;

    private String nationality;

    private String phone;

    private String email;

    // Passport Information
    private String passportNumber;

    private LocalDate passportIssueDate;

    private LocalDate passportExpiryDate;

    private String passportIssuePlace;

    // Emergency Contact
    private String emergencyContactName;

    private String emergencyContactPhone;

    private String emergencyContactRelation;

    // Address
    private String country;

    private String city;

    private String address;

    // Status
    @Builder.Default
    private Boolean active = true;
}