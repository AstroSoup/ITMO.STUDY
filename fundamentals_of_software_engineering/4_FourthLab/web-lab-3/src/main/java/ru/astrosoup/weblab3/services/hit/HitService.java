package ru.astrosoup.weblab3.services.hit;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import ru.astrosoup.weblab3.DAOs.authorisation.UserRepository;
import ru.astrosoup.weblab3.DAOs.hit.HitRepository;
import ru.astrosoup.weblab3.DTOs.authorisation.JwtDto;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitDto;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitResponse;
import ru.astrosoup.weblab3.entities.authorisation.UserEntity;
import ru.astrosoup.weblab3.entities.hit.HitEntity;
import ru.astrosoup.weblab3.exceptions.InvalidHitRequestException;
import ru.astrosoup.weblab3.exceptions.UserDoesNotExistException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Dependent
@Stateless
public class HitService {

    @Inject
    UserRepository userRepository;
    @Inject
    HitRepository hitRepository;

    private static final Logger logger = Logger.getLogger(HitService.class.getName());

    public AreaHitResponse addHit(AreaHitDto request) throws UserDoesNotExistException, InvalidHitRequestException {

        Optional<UserEntity> optionalUser = userRepository.findById(request.getUser().getId());
        if (optionalUser.isEmpty()) {
            throw new UserDoesNotExistException("Hit isn`t registered. User not found.");
        }

        if (!validateRequest(request)) {
            throw new InvalidHitRequestException("Data in hit request isn`t valid.");
        }

        HitEntity hitEntity = new HitEntity();

        hitEntity.setY(request.getY());
        hitEntity.setX(request.getX());
        hitEntity.setR(request.getR());

        UserEntity userEntity = optionalUser.get();

        hitEntity.setUser(userEntity);
        hitEntity.setHit(isAreaHit(request));
        hitRepository.save(hitEntity);
        AreaHitResponse response = new AreaHitResponse();
        response.setHit(hitEntity.isHit());
        response.setR(hitEntity.getR());
        response.setY(hitEntity.getY());
        response.setX(hitEntity.getX());
        response.setDate(hitEntity.getDate());
        return response;
    }

    public List<AreaHitResponse> getHits(JwtDto jwtDto) throws UserDoesNotExistException {
        Optional<UserEntity> optionalUser = userRepository.findById(jwtDto.getId());
        if (optionalUser.isEmpty()) {
            throw new UserDoesNotExistException("User not found.");
        }
        UserEntity userEntity = optionalUser.get();
        List<HitEntity> hitEntities = hitRepository.findByUser(userEntity);
        List<AreaHitResponse> result = new ArrayList<>();
        for (HitEntity hitEntity : hitEntities) {
            AreaHitResponse areaHitResponse = new AreaHitResponse();
            areaHitResponse.setY(hitEntity.getY());
            areaHitResponse.setX(hitEntity.getX());
            areaHitResponse.setR(hitEntity.getR());
            areaHitResponse.setHit(hitEntity.isHit());
            areaHitResponse.setDate(hitEntity.getDate());

            result.add(areaHitResponse);
        }
        return result;
    }

    private boolean isAreaHit(AreaHitDto request) {
        int r = request.getR();
        float x = request.getX();
        float y = request.getY();

        if (x > 0 && y < 0) return false;
        if (x >= 0 && y >= 0) return Math.sqrt(x * x + y * y) <= r;
        if (x < 0 && y >= 0) return x >= -r/2f && y <= r;
        if (x <= 0 && y < 0) return y >= -2 * x - r;
        return false;
    }
    private boolean validateRequest(AreaHitDto request) {
        return request.getR() < 100;
//        return Arrays.asList(1, 2, 3, 4, 5).contains(request.getR())
//                && Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3).contains(request.getX())
//                && request.getY() >= -3 && request.getY() <= 5;
    }


}
