package com.example.OTP_Validator.Repository;

import com.example.OTP_Validator.Models.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<Otp,Integer> {

    Otp findByOtpCode(String otp);
}
