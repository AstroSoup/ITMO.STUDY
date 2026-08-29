package ru.astrosoup.weblab3;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import ru.astrosoup.weblab3.controllers.BasicExceptionMapper;
import ru.astrosoup.weblab3.controllers.authorisation.AuthorisationController;
import ru.astrosoup.weblab3.controllers.hit.HitController;
import ru.astrosoup.weblab3.filters.AuthorisationFilter;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class LabApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthorisationController.class);
        classes.add(HitController.class);
        classes.add(BasicExceptionMapper.class);
        classes.add(AuthorisationFilter.class);
        return classes;
    }
}