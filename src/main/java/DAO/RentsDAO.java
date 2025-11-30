package DAO;

import Database.DBConnection;
import models.RentsModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentsDAO {

    public static List<RentsModel> getAllRents() {
        List<RentsModel> rentsList = new ArrayList<>();
        String sql = "SELECT * FROM rents";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rentsList.add(new RentsModel(
                        rs.getInt("id"),
                        rs.getString("car_registration"),
                        rs.getString("customer_name"),
                        rs.getString("rent_date"),
                        rs.getString("return_date"),
                        rs.getDouble("total_paid")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentsList;
    }

    public static boolean addRent(RentsModel rent) {
        String sql = "INSERT INTO rents (car_registration, customer_name, rent_date, return_date, total_paid) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rent.getCarRegistration());
            pstmt.setString(2, rent.getCustomerName());
            pstmt.setString(3, rent.getRentDate());
            pstmt.setString(4, rent.getReturnDate());
            pstmt.setDouble(5, rent.getTotalPaid());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateRent(RentsModel rent) {
        String sql = "UPDATE rents SET car_registration=?, customer_name=?, rent_date=?, return_date=?, total_paid=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rent.getCarRegistration());
            pstmt.setString(2, rent.getCustomerName());
            pstmt.setString(3, rent.getRentDate());
            pstmt.setString(4, rent.getReturnDate());
            pstmt.setDouble(5, rent.getTotalPaid());
            pstmt.setInt(6, rent.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteRent(int id) {
        String sql = "DELETE FROM rents WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
