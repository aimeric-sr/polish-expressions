package epsi.b3.polish.utils;

import epsi.b3.polish.beans.Score;
import epsi.b3.polish.beans.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

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

    public static UserAccount findUser(Connection conn, String userName) throws SQLException {

        String sql = "Select a.User_Name, a.Password from User_Account a "//
                + " where a.User_Name = ? ";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            String password = rs.getString("Password");
            System.out.println( "username : " + rs.getString("User_Name"));
            UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }

    public static void insertUser(Connection conn, UserAccount user) throws SQLException {
        String sql = "Insert into User_Account(User_Name, Password) values (?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, user.getUserName());
        pstm.setString(2, user.getPassword());

        pstm.executeUpdate();
    }

    public static List<Score> queryProduct(Connection conn) throws SQLException {
        String sql = "Select a.Score, a.User_Name from Score a ";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        List<Score> list = new ArrayList<Score>();
        while (rs.next()) {
            int score = rs.getInt("Score");
            String userName = rs.getString("User_Name");
            Score product = new Score();
            product.setScore(score);
            product.setUserName(userName);
            list.add(product);
        }
        return list;
    }

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
/*
    public static void updateProduct(Connection conn, Score product) throws SQLException {
        String sql = "Update Product set Name =?, Price=? where Code=? ";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, product.getName());
        pstm.setFloat(2, product.getPrice());
        pstm.setString(3, product.getCode());
        pstm.executeUpdate();
    }
*/
    public static void insertProduct(Connection conn, Score score) throws SQLException {
        String sql = "Insert into Scores(Score, User_name) values (?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setInt(1, score.getScore());
        pstm.setString(2, score.getUserName());

        pstm.executeUpdate();
    }

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

/*
    public static void deleteProduct(Connection conn, String code) throws SQLException {
        String sql = "Delete From Product where Code= ?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, code);

        pstm.executeUpdate();
    }
*/
}