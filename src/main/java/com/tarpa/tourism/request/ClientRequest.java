package com.tarpa.tourism.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequest {

    // Personal Information

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String gender;

    private LocalDate dateOfBirth;

    private String nationality;

    @NotBlank(message = "Phone is required")
    private String phone;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
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
}