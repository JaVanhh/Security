package com.demo.secutity.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String fullName;
}
