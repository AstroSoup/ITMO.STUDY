package ru.astrosoup.weblab3.controllers.hit;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.astrosoup.weblab3.DTOs.authorisation.JwtDto;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitDto;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitRequest;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitResponse;
import ru.astrosoup.weblab3.annotations.AuthorisationBlocked;
import ru.astrosoup.weblab3.exceptions.InvalidHitRequestException;
import ru.astrosoup.weblab3.exceptions.UserDoesNotExistException;
import ru.astrosoup.weblab3.monitoring.HitCheckerMBean;
import ru.astrosoup.weblab3.services.hit.HitService;

import java.util.List;
import java.util.logging.Logger;

@Path("/hit")
@AuthorisationBlocked
public class HitController {

    private static final Logger logger = Logger.getLogger(HitController.class.getName());

    @Inject
    JwtDto user;

    @Inject
    HitService hitService;

    @Inject
    HitCheckerMBean hitChecker;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResults() {
        try {
            List<AreaHitResponse> hits = hitService.getHits(user);
            for (AreaHitResponse hit: hits) {
                logger.info(hit.toString());
            }
            return Response.ok(hits).build();
        } catch (UserDoesNotExistException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHit(AreaHitRequest areaHitRequest) {
        try {
             AreaHitResponse hit = hitService.addHit(new AreaHitDto(user, areaHitRequest));
             hitChecker.addPoint(hit, user.getId());
             return Response.ok(hit).build();
        } catch (InvalidHitRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (UserDoesNotExistException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
