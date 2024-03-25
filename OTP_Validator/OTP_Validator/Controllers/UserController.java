package com.example.OTP_Validator.Controllers;

import com.example.OTP_Validator.Models.User;
import com.example.OTP_Validator.Services.UserService;
import com.example.OTP_Validator.dto.request.OtpRequest;
import com.example.OTP_Validator.dto.request.UserLoginRequest;
import com.example.OTP_Validator.dto.request.UserRegisterRequest;
import com.example.OTP_Validator.dto.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseDto addUser(@RequestBody UserRegisterRequest user) {
        ResponseDto responseDto;
        try{
            responseDto = userService.addUser(user);
        }catch (Exception e){
            responseDto = new ResponseDto(e);
        }
        return responseDto;
    }
    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserLoginRequest user) {
        ResponseDto responseDto;
        try{
            responseDto = userService.login(user);
        }catch (Exception e){
            responseDto = new ResponseDto(e);
        }
        return responseDto;
    }
    @PostMapping("/verify-otp")
    private ResponseDto verifyOtp(@RequestBody OtpRequest otp){
        ResponseDto responseDto;
        try{
            responseDto = userService.verifyOtp(otp);
        }catch (Exception e){
            responseDto = new ResponseDto(e);
        }
        return responseDto;
    }

    @GetMapping("/getUser/{name}")
    public ResponseEntity getUserProfile(@PathVariable ("name") String username) {
        User user=userService.getUser(username);
        if (user==null) return new ResponseEntity("User Not Found",HttpStatus.NOT_FOUND);
        return  new ResponseEntity("User not found",HttpStatus.FOUND);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserProfile(@PathVariable ("userId") int userId, @RequestBody User user) {
        User updatedUser = userService.updateUserProfile(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable ("userId") int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
