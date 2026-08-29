package ru.astrosoup.weblab3.DTOs.hit;

import lombok.Data;
import ru.astrosoup.weblab3.DTOs.authorisation.JwtDto;

@Data
public class AreaHitDto {
    JwtDto user;
    private int r;
    private int x;
    private float y;

    public AreaHitDto(JwtDto user, AreaHitRequest areaHitRequest) {
        this.user = user;
        this.r = areaHitRequest.getR();
        this.x = areaHitRequest.getX();
        this.y = areaHitRequest.getY();
    }

}
