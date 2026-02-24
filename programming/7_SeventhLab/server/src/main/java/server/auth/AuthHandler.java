package server.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthHandler implements shared.AuthHandler {
    private Connection con;
    public AuthHandler() {
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
            psInsert.setString(1, username);
            psInsert.setString(2, password);
            var rs = psCheck.executeQuery();
            if (!rs.next()) {
                return false;
            }
            if (rs.getInt(1) == 0) {
                psInsert.executeUpdate();
                return true;
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
