package ru.astrosoup.weblab3.services.security;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;

@Stateless
@Dependent
public class PasswordHashingService {
    Argon2 argon2;

    private static final int ITERATIONS = 3;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;

    public PasswordHashingService() {
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    public String hash(String hashable) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, hashable);
    }

    public boolean verify(String hash, String hashable) {
        return argon2.verify(hash, hashable);
    }
}
