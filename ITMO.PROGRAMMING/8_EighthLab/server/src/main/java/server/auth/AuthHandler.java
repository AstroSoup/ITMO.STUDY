package server.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class AuthHandler implements shared.AuthHandler {
    private Connection con;
    private static final Logger AUTHLOGGER = Logger.getLogger(AuthHandler.class.getName());
    public AuthHandler() {
        AUTHLOGGER.setUseParentHandlers(true);
        try {
            con = DriverManager.getConnection(
                    "jdbc:postgresql://db:5432/studs",
                    System.getenv("USERNAME"),
                    System.getenv("PASSWORD"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean register(String username, String password) {

        String insert = "INSERT INTO Users(username, password) VALUES (?, ?);";
        String check = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (var psInsert = con.prepareStatement(insert); var psCheck = con.prepareStatement(check)) {
            psCheck.setString(1, username);
            var rs = psCheck.executeQuery();


            if (rs.next()) {
                int isValidUsername = rs.getInt(1);

                if (isValidUsername == 0) {
                    AUTHLOGGER.info(isValidUsername + " " + username);
                    psInsert.setString(1, username);
                    psInsert.setString(2, password);
                    psInsert.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean login(String username, String password) {
        String check = "SELECT password FROM Users WHERE username = ?;";
        try (var psCheck = con.prepareStatement(check)) {
            psCheck.setString(1, username);
            var rs = psCheck.executeQuery();
            if (!rs.next()) {
                return false;
            }
            if (rs.getString(1).equals(password)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
