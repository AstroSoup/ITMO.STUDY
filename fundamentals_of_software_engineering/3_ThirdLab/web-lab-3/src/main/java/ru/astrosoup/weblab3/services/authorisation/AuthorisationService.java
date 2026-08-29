package ru.astrosoup.weblab3.services.authorisation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.antlr.v4.runtime.misc.Pair;
import ru.astrosoup.weblab3.DAOs.authorisation.UserRepository;
import ru.astrosoup.weblab3.DTOs.authorisation.LoginDto;

import ru.astrosoup.weblab3.entities.authorisation.UserEntity;
import ru.astrosoup.weblab3.exceptions.InvalidJwtException;
import ru.astrosoup.weblab3.exceptions.JwtGenerationException;
import ru.astrosoup.weblab3.exceptions.LoginIsNotValidException;
import ru.astrosoup.weblab3.exceptions.UserAlreadyRegisteredException;
import ru.astrosoup.weblab3.services.security.JwtService;
import ru.astrosoup.weblab3.services.security.PasswordHashingService;

import java.util.Optional;

@ApplicationScoped
public class AuthorisationService {

    @Inject
    private PasswordHashingService passwordHashingService;
    @Inject
    private UserRepository userRepository;
    @Inject
    JwtService jwtService;

    public Pair<String,String> register(LoginDto user) throws JwtGenerationException, UserAlreadyRegisteredException {
        String hashedPassword = passwordHashingService.hash(user.getPassword());
        String username = user.getUsername();
        Optional<UserEntity> optionalUser = userRepository.findByName(username);
        UserEntity userEntity;
        if (optionalUser.isEmpty()) {
            userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPasswordHash(hashedPassword);
            userEntity = userRepository.save(userEntity);
        } else {
            throw new UserAlreadyRegisteredException("The username is taken.");
        }

        try {
            return new Pair<>(jwtService.generateToken(userEntity, JwtService.TokenLiveSpan.SHORT),
                    jwtService.generateToken(userEntity, JwtService.TokenLiveSpan.LONG));
        } catch (Exception e) {
            throw new JwtGenerationException(e.getMessage());
        }
    }

    public Pair<String, String> login(LoginDto user) throws JwtGenerationException, LoginIsNotValidException {
        String username = user.getUsername();
        String password = user.getPassword();
        Optional<UserEntity> optionalUserEntity = userRepository.findByName(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            if (passwordHashingService.verify(userEntity.getPasswordHash(), password)) {
                try {
                    return new Pair<>(jwtService.generateToken(userEntity, JwtService.TokenLiveSpan.SHORT),
                            jwtService.generateToken(userEntity, JwtService.TokenLiveSpan.LONG));
                } catch (Exception e) {
                    throw new JwtGenerationException(e.getMessage());
                }
            }
            throw new LoginIsNotValidException("Incorrect password");
        }
        throw new LoginIsNotValidException("No such user");
    }

    public Pair<String, String> refresh(String refreshToken) throws JwtGenerationException, InvalidJwtException {
        JsonObject claims = jwtService.validateTokenAndGetClaims(refreshToken);
        // todo cache
        Optional<UserEntity> optionalUser = userRepository.findById(claims.getJsonNumber("upn").longValue());
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            try {
                jwtService.revokeJti(claims.getString("jti"));
                return new Pair<>(jwtService.generateToken(userEntity, JwtService.TokenLiveSpan.SHORT),
                        jwtService.generateToken(userEntity, JwtService.TokenLiveSpan.LONG));
            } catch (Exception e) {
                throw new JwtGenerationException(e.getMessage());
            }
        }
        throw new JwtGenerationException("No such user");
    }

    public void logout(String jwt) throws InvalidJwtException {
        JsonObject claims = jwtService.validateTokenAndGetClaims(jwt);
        jwtService.revokeJti(claims.getString("jti"));
    }

}
