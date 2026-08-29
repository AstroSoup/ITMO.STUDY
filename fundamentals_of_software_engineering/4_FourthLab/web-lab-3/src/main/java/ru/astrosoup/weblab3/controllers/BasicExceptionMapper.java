package ru.astrosoup.weblab3.controllers;

import jakarta.json.bind.JsonbException;
import jakarta.json.stream.JsonParsingException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Arrays;
import java.util.stream.Collectors;

@Provider
public class BasicExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof ProcessingException ex) {
            String s = Arrays.stream(ex.getSuppressed())
                    .map(Object::getClass)
                    .map(Class::getName)
                    .collect(Collectors.joining(", "));
            s = s.isEmpty() ? "Request has invalid fields" : s;
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \""+ s +"\"}")
                    .type("application/json")
                    .build();
        }

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\": \"Internal server error: " + exception.getClass().getName() + "\"}")
                .type("application/json")
                .build();
    }
}
