package epsi.b3.polish.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {

    public static Connection getMySQLConnection()
            throws ClassNotFoundException, SQLException {
        // Remarque: Modifiez les paramètres de connexion en conséquence.
        String hostName = "localhost";
        String dbName = "polish";
        String userName = "root";
        String password = "";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        // La structure de URL Connection pour MySQL:
        // Exemple:
        // jdbc:mysql://localhost:3306/simplehr
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?characterEncoding=latin1";

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}