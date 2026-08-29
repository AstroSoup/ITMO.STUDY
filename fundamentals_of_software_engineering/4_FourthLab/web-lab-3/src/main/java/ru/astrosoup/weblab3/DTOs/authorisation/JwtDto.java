package ru.astrosoup.weblab3.DTOs.authorisation;

import jakarta.enterprise.context.RequestScoped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RequestScoped
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {
    private Long id;
    private String group;
}
