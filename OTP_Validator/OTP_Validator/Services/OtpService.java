package com.example.OTP_Validator.Services;

import com.example.OTP_Validator.Models.Otp;
import com.example.OTP_Validator.Models.User;
import com.example.OTP_Validator.Repository.OTPRepository;
import com.example.OTP_Validator.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {
    @Autowired
    OTPRepository otpRepository;

    @Autowired
    UserRepository userRepository;

    // Function to generate and send OTP
    public void sendOTP(String recipientPhoneNoOrEmailId, String otp) {
        User user=new User();
        //check whether recipient exists or not
        if(userRepository.findByContact(recipientPhoneNoOrEmailId)==null && userRepository.findByEmail(recipientPhoneNoOrEmailId)==null )
        { throw new RuntimeException("PhoneNo or Email does not exists");}
        // Save OTP to database
        Otp otpCode = new Otp();
        otpCode.setOtpCode(otp);
        otpCode.setTarget(user.getContact() != null ? user.getContact() : user.getEmail());
        otpRepository.save(otpCode);

    }

    // Function to validate OTP
    public boolean validateOtp(String otp, String target) {
        // Retrieve OTP from the database based on the target (phone number or email)
        Otp otp1 = otpRepository.findByOtpCode(otp);
        if(otp1==null){
            throw new RuntimeException("Invalid output");
        }
        // Check if OTP exists and matches the provided OTP

        if(userRepository.findByEmail(otp1.getTarget())==null && userRepository.findByContact(otp1.getTarget())==null) {
            throw new RuntimeException("User doesn't exists");
        }
                // Delete the OTP from the database after successful validation
                otpRepository.delete(otp1);
                return true; // OTP is valid
    }
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

}
