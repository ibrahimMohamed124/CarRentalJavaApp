package Database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sqlCars = """
                CREATE TABLE IF NOT EXISTS cars (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    registration TEXT UNIQUE NOT NULL,
                    brand TEXT NOT NULL,
                    model TEXT NOT NULL,
                    status TEXT NOT NULL,
                    price REAL NOT NULL
                );
            """;

            String sqlRents = """
                CREATE TABLE IF NOT EXISTS rents (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    car_registration TEXT NOT NULL,
                    customer_name TEXT NOT NULL,
                    rent_date TEXT NOT NULL,
                    return_date TEXT NOT NULL,
                    total_paid REAL NOT NULL,
                    FOREIGN KEY(car_registration) REFERENCES cars(registration)
                );
            """;

            stmt.execute(sqlCars);
            stmt.execute(sqlRents);

            System.out.println("✅ Database initialized.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
