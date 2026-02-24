package server.storage;

import shared.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;

public class DBHandler {
    private Connection con;
    public DBHandler() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:postgresql://db:5432/studs",
                    System.getenv("USERNAME"),
                    System.getenv("PASSWORD"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized Vector<Ticket> readAllTickets() {
        Vector<Ticket> tickets = new Vector<>();
        String sql = "select * from collection";
        try(var ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String type = rs.getString("ticket_type");
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setName(rs.getString("name"));
                ticket.setPrice(rs.getLong("price"));
                ticket.setDiscount(rs.getFloat("discount"));
                ticket.setCoordinates(new Coordinates(rs.getFloat("coordinate_x"), rs.getDouble("coordinate_y")));
                ticket.setPerson(new Person(rs.getString("passportID"), Color.valueOf(rs.getString("eye_color").toUpperCase()),
                        new Location(rs.getLong("location_x"), rs.getLong("location_y"), rs.getLong("location_z"))));
                ticket.setType(type == null ? null : TicketType.valueOf(type.toUpperCase()));
                ticket.setCreator(rs.getString("who_created"));
                ticket.setCreationDate(((Date) rs.getObject("creation_date")).toLocalDate());
                tickets.add(ticket);
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return tickets;
    }

    public synchronized boolean write(Ticket ticket) {
        String insert = """
                INSERT INTO Collection(name, price, discount, coordinate_x, coordinate_y, ticket_type, passportID, eye_color, location_x, location_y, location_z, creation_date, who_created)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, ?);""";
        try (var psInsert = con.prepareStatement(insert)) {
            psInsert.setString(1, ticket.getName());
            psInsert.setLong(2, ticket.getPrice());
            psInsert.setFloat(3, ticket.getDiscount());
            psInsert.setFloat(4, ticket.getCoordinates().getX());
            psInsert.setDouble(5, ticket.getCoordinates().getY());
            if (ticket.getType() == null) psInsert.setNull(6, Types.VARCHAR);
            else psInsert.setString(6, ticket.getType().toString());
            psInsert.setString(7, ticket.getPerson().getPassportID());
            psInsert.setString(8, ticket.getPerson().getEyeColor().toString());
            psInsert.setLong(9,ticket.getPerson().getLocation().getX());
            psInsert.setLong(10,ticket.getPerson().getLocation().getY());
            psInsert.setLong(11,ticket.getPerson().getLocation().getZ());
            psInsert.setString(12, ticket.getCreator());
            psInsert.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public synchronized boolean remove(int id) {
        String remove = """
                DELETE FROM Collection WHERE id = ?;
                """;
        try (var psRemove = con.prepareStatement(remove)) {
            psRemove.setInt(1, id);
            psRemove.executeUpdate();
            return true;
        } catch ( SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public synchronized boolean removeAll() {
        String removeAll = """
                TRUNCATE TABLE Collection;
                """;
        try (var psRemove = con.prepareStatement(removeAll)) {
            psRemove.executeUpdate();
            return true;
        } catch ( SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public synchronized boolean update(Ticket ticket) {
        String update = """
                UPDATE Collection
                SET name = ?,
                price = ?,
                discount = ?,
                coordinate_x = ?,
                coordinate_y = ?,
                ticket_type = ?,
                passportID = ?,
                eye_color = ?,
                location_x = ?,
                location_y = ?,
                location_z = ?
                WHERE id = ?;
                """;
        try(var psUpdate = con.prepareStatement(update)) {
            psUpdate.setString(1, ticket.getName());
            psUpdate.setLong(2, ticket.getPrice());
            psUpdate.setFloat(3, ticket.getDiscount());
            psUpdate.setFloat(4, ticket.getCoordinates().getX());
            psUpdate.setDouble(5, ticket.getCoordinates().getY());
            if (ticket.getType() == null) psUpdate.setNull(6, Types.VARCHAR);
            else psUpdate.setString(6, ticket.getType().toString());
            psUpdate.setString(7, ticket.getPerson().getPassportID());
            psUpdate.setString(8, ticket.getPerson().getEyeColor().toString());
            psUpdate.setLong(9,ticket.getPerson().getLocation().getX());
            psUpdate.setLong(10,ticket.getPerson().getLocation().getY());
            psUpdate.setLong(11,ticket.getPerson().getLocation().getZ());
            psUpdate.setInt(12, ticket.getId());
            psUpdate.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
