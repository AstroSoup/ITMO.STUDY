package ru.astrosoup.weblab3.DTOs.hit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaHitResponse {
    private int r;
    private int x;
    private float y;
    private boolean hit;
    private LocalDate date;
}
