package com.example.OTP_Validator.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Size(min=3, max=20, message="Please enter valid input")
    @Column(nullable = false)
    private String name;

    @Size(min=8, max=20, message="Please enter valid password")
    @Column(nullable = false)
    private String password;

    @Email(message = "Please Enter valid email")
    @Column(nullable = false)
    private String email;

    @Size(min=10, max=10, message="Please enter valid input")
    @Column(nullable = false)
    private String contact;

    private String otp;

}
