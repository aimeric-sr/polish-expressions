package epsi.b3.polish.conn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Permet de gérer la connexion à la base de données.
 */
public class ConnectionUtils {

    /**
     * Fonction permettant de d'accéder à la connexion de la base de données.
     *
     * @return la connexion à la base de données
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection()
            throws ClassNotFoundException, SQLException, IOException {

        // Connexion à la base de données MySQL
        return MySQLConnUtils.getMySQLConnection();
    }

    /**
     * Fonction permettant de fermer la connexion avec la base de données.
     *
     * @param conn
     */
    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

    /**
     * Fonction permettant d'annuler les requêtes que l'on vient de réaliser
     * depuis la base de données.
     *
     * @param conn
     */
    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }
}