package org.example.clearsolutionstest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(nullable = false)
    private String firstName;
    //@Column(nullable = false)
    private String lastName;
    //@Column(nullable = false)
    private LocalDate dateOfBirth;
    //@Column(nullable = false)
    private String email;
    private String address;
    private String phoneNumber;
}
