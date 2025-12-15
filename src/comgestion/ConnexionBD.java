package comgestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {

 private static final String URL =
    "jdbc:oracle:thin:@localhost:1521:XE";


    private static final String USER = "gestion";     // ou ton user
    private static final String PASSWORD = "gestion123"; // ton mot de passe

    private static Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("oracle.jdbc.OracleDriver"); // driver moderne
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion Oracle réussie");
            } catch (ClassNotFoundException e) {
                throw new SQLException("❌ Driver Oracle introuvable", e);
            }
        }
        return connection;
    }
}
