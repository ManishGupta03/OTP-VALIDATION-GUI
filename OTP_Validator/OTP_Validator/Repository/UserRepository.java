package com.example.OTP_Validator.Repository;

import com.example.OTP_Validator.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String s);

    User findByName(String username);

    User findById(int id);

    User findByContact(String contact);

    boolean existsByContact(String contact);
}
