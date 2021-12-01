package epsi.b3.polish.conn;

import java.io.*;
import java.lang.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connecte la base de données MySQL à notre application.
 */
public class MySQLConnUtils {

    /**
     * Permet la connexion à la base de données avec les paramètres désignés dans le corps de la fonction.
     *
     * @return la connexion à la base de données
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getMySQLConnection()
            throws ClassNotFoundException, SQLException, IOException {
        // Remarque: Modifiez les paramètres de connexion en conséquence.
        String hostName = "localhost";
        String dbName = "polish";
        String userName = "root";
        String password = "";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    /**
     * Permet la connexion à la base de données en prenant en paramètres ce qui suit :
     *
     * @param hostName le nom de d'hôte
     * @param dbName   le nom de la base de données
     * @param userName le nom de l'utilisateur connecté à la base de données
     * @param password le mot de passe lié à l'utilisateur
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @returnla connexion à la base de données
     */
    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException, IOException {


        Class.forName("com.mysql.jdbc.Driver");

        // La structure de URL Connection pour MySQL:
        // jdbc:mysql://localhost:3306/le_nom_de_la_base_de_données
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?characterEncoding=latin1";

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}