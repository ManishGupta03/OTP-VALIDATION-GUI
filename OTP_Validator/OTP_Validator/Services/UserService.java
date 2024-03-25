package com.example.OTP_Validator.Services;

import com.example.OTP_Validator.Models.User;
import com.example.OTP_Validator.Repository.UserRepository;
import com.example.OTP_Validator.dto.request.OtpRequest;
import com.example.OTP_Validator.dto.request.UserLoginRequest;
import com.example.OTP_Validator.dto.request.UserRegisterRequest;
import com.example.OTP_Validator.dto.response.ResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.OTP_Validator.Services.OtpService.generateOTP;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JavaMailSender emailSender;
    public ResponseDto addUser(UserRegisterRequest request) {
        ResponseDto responseDto = new ResponseDto();
        if(request.getEmail().isEmpty() || request.getContact().isEmpty() || request.getName().isEmpty() || request.getPassword().isEmpty()){
            responseDto.setMessage("Enter Valid Credentials");
        }
        else{
            if(!emailValidation(request.getEmail())) {
                responseDto.setMessage("Enter Valid Mail Id");
            }
            else{
                //check user already signed in or not
                if(userRepository.existsByContact(request.getContact())){
                    responseDto.setMessage("USER EXISTS, PLEASE GO TO LOGIN PAGE");
                }
                else{
                    User u = new User();
                    u.setName(request.getName());
                    u.setPassword(request.getPassword());
                    u.setEmail(request.getEmail());
                    u.setContact(request.getContact());
                    userRepository.save(u);
                    responseDto.setMessage("USER REGISTERED SUCCESSFULLY");
                    responseDto.setStatus("SUCCESS");
                    responseDto.setStatusCode(200);
                }
            }
        }
        return responseDto;
    }
    public ResponseDto login(UserLoginRequest request) {
        ResponseDto responseDto = new ResponseDto();
        if(request.getPassword().isEmpty() || request.getContact().isEmpty()){
            responseDto.setMessage("Enter Valid Credentials");
        }
        else{
            User u = userRepository.findByContact(request.getContact());
            if(u == null){
                responseDto.setMessage("USER NOT EXISTS");
            }
            else{
                String password = u.getPassword();
                if(!request.getPassword().equalsIgnoreCase(password)){
                    responseDto.setMessage("Password doesn't matches");
                }
                else{
                    String otp = generateOTP();
                    u.setOtp(otp);
                    userRepository.save(u);
                    sendEmail(u.getEmail(), "OTP For Login", otp);
                    responseDto.setMessage("OTP SEND SUCCESSFULLY TO MAIL");
                    responseDto.setStatus("SUCCESS");
                    responseDto.setStatusCode(200);
                }
            }
        }
        return responseDto;
    }

    public void sendEmail(String to, String subject, String otp) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("OTP : "+otp+"\n,   Note: This OTP expires within 2 minutes from the time of generation", true); // Use true to indicate HTML content
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    public User getUser(String username) {
        return userRepository.findByName(username);
    }

    public User updateUserProfile(int userId, User user) {
        User user1=userRepository.findById(userId);
        if(user1==null) throw new RuntimeException("user not exists");
        return userRepository.save(user);
    }

    public void deleteUser(int userId) {
        User user=userRepository.findById(userId);
        if(user==null) throw new RuntimeException("user not exists");
        userRepository.delete(user);
    }
    public static boolean emailValidation(String email){
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public ResponseDto verifyOtp(OtpRequest otp) {
        ResponseDto responseDto = new ResponseDto();
        User u = userRepository.findById(otp.getId());
        if(otp.getOtp().equalsIgnoreCase(u.getOtp())){
            responseDto.setMessage("OTP VERIFIED");
            responseDto.setStatusCode(200);
            responseDto.setStatus("SUCCESS");
        }
        else{
            responseDto.setMessage("OTP EXPIRES OR INVALID");
        }
        return responseDto;
    }
}
