package com.example.OTP_Validator;

import com.example.OTP_Validator.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class OtpValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpValidatorApplication.class, args);
	}

}
