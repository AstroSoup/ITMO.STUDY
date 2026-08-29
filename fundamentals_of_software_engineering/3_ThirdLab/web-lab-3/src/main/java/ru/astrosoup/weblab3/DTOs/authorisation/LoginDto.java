package ru.astrosoup.weblab3.DTOs.authorisation;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
