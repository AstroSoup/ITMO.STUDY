package ru.astrosoup.weblab3.DTOs.hit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaHitRequest {
    private int r;
    private int x;
    private float y;
}
