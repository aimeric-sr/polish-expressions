package epsi.b3.polish.conn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection()
            throws ClassNotFoundException, SQLException, IOException {

        // Connection à la base de données MySQL
        return MySQLConnUtils.getMySQLConnection();
    }

    /**
     * Fonction permettant de fermer la connection avec la base de données.
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