package epsi.b3.polish.utils;

import epsi.b3.polish.beans.Score;
import epsi.b3.polish.beans.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Permet toutes les actions sur la base de données
 */
public class DBUtils {

    /**
     * Retourne un utilisateur en fonction de son nom et de son mot de passe,
     * s'il existe.
     *
     * @param conn
     * @param userName son nom d'utilisateur
     * @param password son mot de passe
     * @return l'utilsateur recherché
     * @throws SQLException
     */
    public static UserAccount findUser(Connection conn, //
                                       String userName, String password) throws SQLException {

        String sql = "Select a.User_Name, a.Password from User_Account a " //
                + " where a.User_Name = ? and a.password= ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }

    /**
     * Retourne un utilisateur en fonction de seulement son nom,
     * s'il existe.
     *
     * @param conn
     * @param userName son nom
     * @return l'utilsateur recherché
     * @throws SQLException
     */
    public static UserAccount findUser(Connection conn, String userName) throws SQLException {

        String sql = "Select a.User_Name, a.Password from User_Account a "//
                + " where a.User_Name = ? ";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            String password = rs.getString("Password");
            UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }

    /**
     * Permet d'ajouter un utilisateur à la base de données.
     *
     * @param conn
     * @param user un utilisateur
     * @throws SQLException
     */
    public static void insertUser(Connection conn, UserAccount user) throws SQLException {
        String sql = "Insert into User_Account(User_Name, Password) values (?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, user.getUserName());
        pstm.setString(2, user.getPassword());

        pstm.executeUpdate();
    }

    /**
     * Permet de trouver les dix meilleurs scores d'un utilisateur.
     *
     * @param conn
     * @param userName le nom de l'utilisateur
     * @return une liste d'entier correspondant aux 10 meileurs scores
     * de l'utilisateur, par ordre décroissant
     * @throws SQLException
     */
    public static ArrayList<Integer> findMyScores(Connection conn, String userName) throws SQLException {
        String sql = "Select a.Score from Scores a where a.User_Name=? order by a.Score DESC LIMIT 10";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);

        ResultSet rs = pstm.executeQuery();

        ArrayList<Integer> scores = new ArrayList<>();

        while (rs.next()) {
            int score = rs.getInt("Score");
            scores.add(score);
        }
        return scores;
    }

    /**
     * Permet de trouver les dix meilleurs scores au total, ainsi que les utilisateurs ayant fait ces scores.
     * @param conn
     * @return une liste de correspondant aux 10 meileurs scores, avec le nom des joueurs, par ordre décroissant
     * @throws SQLException
     */
    public static ArrayList<Score> findBestScores(Connection conn) throws SQLException {
        String sql = "Select a.Score, a.User_Name from Scores a order by a.Score DESC LIMIT 10";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        ArrayList<Score> scores = new ArrayList<>();

        while (rs.next()) {
            int score = rs.getInt("Score");
            String userName = rs.getString("User_Name");
            Score result = new Score();
            result.setUserName(userName);
            result.setScore(score);
            scores.add(result);
        }
        return scores;
    }

    /**
     * Permet d'ajouter un nouveau score à la base de données.
     * @param conn
     * @param score un objet Score comprenant le score et le nom de l'utilisateur.
     * @throws SQLException
     */
    public static void insertScore(Connection conn, Score score) throws SQLException {
        String sql = "Insert into Scores(Score, User_name) values (?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setInt(1, score.getScore());
        pstm.setString(2, score.getUserName());

        pstm.executeUpdate();
    }

    /**
     * Permet d'obtenir le dernier score fait par un joueur pour afficher son score sur la page de résultat.
     * @param conn
     * @param userName le nom de l'utilisateur
     * @return son dernier score en date
     * @throws SQLException
     */
    public static int LatestScore(Connection conn, String userName) throws SQLException {
        String sql = "Select id, Score from Scores where User_Name=? ORDER BY id DESC LIMIT 1";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            int score = rs.getInt("score");
            return score;
        }
        return 0;
    }
}