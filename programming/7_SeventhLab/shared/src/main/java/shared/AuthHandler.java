package shared;

public interface AuthHandler {
    boolean login(String username, String password);
    boolean register(String username, String password);
}
