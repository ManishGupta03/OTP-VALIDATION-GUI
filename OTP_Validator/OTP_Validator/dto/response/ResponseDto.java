package com.example.OTP_Validator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private int statusCode = 404;
    private String status = "NOT FOUND";
    private String message;
    private Object data;

    public ResponseDto(Exception e){
        this.status="Internal Error";
        this.statusCode=500;
        this.message=e.getMessage();
    }
}
