package ru.astrosoup.weblab3.controllers.authorisation;

import jakarta.inject.Inject;


import jakarta.ws.rs.*;

import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.antlr.v4.runtime.misc.Pair;
import ru.astrosoup.weblab3.DTOs.authorisation.LoginDto;

import ru.astrosoup.weblab3.annotations.AuthorisationBlocked;
import ru.astrosoup.weblab3.exceptions.InvalidJwtException;
import ru.astrosoup.weblab3.exceptions.JwtGenerationException;
import ru.astrosoup.weblab3.exceptions.LoginIsNotValidException;
import ru.astrosoup.weblab3.exceptions.UserAlreadyRegisteredException;
import ru.astrosoup.weblab3.services.authorisation.AuthorisationService;

import java.util.logging.Logger;


@Path("/auth")
public class AuthorisationController {

    // TODO: Logger через аннотации
    private static Logger logger = Logger.getLogger(AuthorisationController.class.getName());

    @Inject
    AuthorisationService authorisationService;

    @Path("/register") @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(LoginDto loginDto) {
        try {
            Pair<String, String> jwts = authorisationService.register(loginDto);
// todo reuse
            NewCookie jwt = new NewCookie.Builder("refresh-jwt")
                    .httpOnly(true)
                    .maxAge(7 * 24 * 60 * 60)
                    .secure(true)
                    .sameSite(NewCookie.SameSite.LAX)
                    .value(jwts.b)
                    .build();

            return Response.ok("Registered successfully")
                    .header("Authorization", "Bearer " + jwts.a)
                    .cookie(jwt)
                    .build();
        } catch (JwtGenerationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (UserAlreadyRegisteredException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Path("/login") @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(LoginDto loginDto) {
        try {
            Pair<String, String> jwts = authorisationService.login(loginDto);

            NewCookie jwt = new NewCookie.Builder("refresh-jwt")
                    .httpOnly(true)
                    .maxAge(7 * 24 * 60 * 60)
                    .secure(true)
                    .sameSite(NewCookie.SameSite.LAX)
                    .value(jwts.b)
                    .build();
            return Response.ok("Logged in successfully")
                    .header("Authorization", "Bearer " + jwts.a)
                    .cookie(jwt)
                    .build();
        } catch (LoginIsNotValidException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (JwtGenerationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/refresh") @GET
    public Response refresh(@CookieParam("refresh-jwt") Cookie cookie) {
        try {
            Pair<String, String> jwts = authorisationService.refresh(cookie.getValue());
            NewCookie jwt = new NewCookie.Builder("refresh-jwt")
                    .httpOnly(true)
                    .maxAge(7 * 24 * 60 * 60)
                    .secure(true)
                    .sameSite(NewCookie.SameSite.LAX)
                    .value(jwts.b)
                    .build();
            return Response.ok("Refreshed successfully")
                    .header("Authorization", "Bearer " + jwts.a)
                    .cookie(jwt)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @Path("/logout") @GET
    public Response logout(@HeaderParam("Authorization") String authHeader, @CookieParam("refresh-jwt") Cookie cookie) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String shortJwt = authHeader.replace("Bearer ", "");
        String longJwt = cookie.getValue();

        try {
            authorisationService.logout(shortJwt);
            authorisationService.logout(longJwt);
        } catch (InvalidJwtException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        NewCookie jwt = new NewCookie.Builder("refresh-jwt")
                .httpOnly(true)
                .maxAge(0)
                .secure(true)
                .sameSite(NewCookie.SameSite.LAX)
                .value("")
                .build();
        return Response.ok("Logged out successfully").cookie(jwt).build();
    }

    @Path("/ping") @GET
    @AuthorisationBlocked
    public Response ping() {
        return Response.ok("Pong!").build();
    }
}
