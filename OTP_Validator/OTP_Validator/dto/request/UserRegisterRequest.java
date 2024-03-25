package com.example.OTP_Validator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String name;
    private String password;
    private String email;
    private String contact;
}
