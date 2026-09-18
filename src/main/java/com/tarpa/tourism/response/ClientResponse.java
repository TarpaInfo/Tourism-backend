package com.tarpa.tourism.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate dateOfBirth;

    private String nationality;

    private String phone;

    private String email;

    private String passportNumber;

    private LocalDate passportIssueDate;

    private LocalDate passportExpiryDate;

    private String passportIssuePlace;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private String emergencyContactRelation;

    private String country;

    private String city;

    private String address;

    private Boolean active;
}