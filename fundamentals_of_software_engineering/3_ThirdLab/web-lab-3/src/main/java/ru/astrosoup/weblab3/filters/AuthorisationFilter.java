package ru.astrosoup.weblab3.filters;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import ru.astrosoup.weblab3.DTOs.authorisation.JwtDto;
import ru.astrosoup.weblab3.annotations.AuthorisationBlocked;
import ru.astrosoup.weblab3.exceptions.InvalidJwtException;
import ru.astrosoup.weblab3.services.security.JwtService;

import java.io.IOException;
import java.util.logging.Logger;

@Provider
@AuthorisationBlocked
public class AuthorisationFilter implements ContainerRequestFilter {

    @Inject
    private JwtService jwtService;

    @Inject
    JwtDto user;

    private static final Logger logger = Logger.getLogger(AuthorisationFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("Authorization header is missing");
            logger.info("Header: " + authHeader);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        String token =  authHeader.replace("Bearer ", "");

        try {
            JsonObject claims = jwtService.validateTokenAndGetClaims(token);
            user.setId(claims.getJsonNumber("upn").longValue());
            user.setGroup(claims.getString("group"));
        } catch (InvalidJwtException e) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build());
        }



    }
    

}