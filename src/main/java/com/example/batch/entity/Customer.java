package com.example.batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String company;
    private String city;
    private String country;
    private String contactNo;
    private String email;
    private String dob;
}
