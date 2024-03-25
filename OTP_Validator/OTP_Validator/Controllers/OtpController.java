package com.example.OTP_Validator.Controllers;

import com.example.OTP_Validator.Services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    @Autowired
    OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity sendOtp(@RequestParam String recipientPhoneNoOrEmailId) {
        String generatedOtp = otpService.generateOTP();
        //this will give a randomly generated 6 digit unique code
        otpService.sendOTP(recipientPhoneNoOrEmailId, generatedOtp);
        return new ResponseEntity("OTP sent successfully", HttpStatus.CREATED);
    }

    @PostMapping("/validate/{otp}/{PhoneOrEmail}")
    public ResponseEntity validateOtp(@PathVariable ("otp") String otp, @PathVariable ("PhoneOrEmail") String target) {
        if (!otpService.validateOtp(otp,target)) {
            return new ResponseEntity("Invalid OTP",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity ("OTP Verified Successfully" , HttpStatus.CREATED);
    }
}
