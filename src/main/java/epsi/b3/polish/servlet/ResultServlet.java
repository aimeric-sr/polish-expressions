package epsi.b3.polish.servlet;

import epsi.b3.polish.beans.Score;
import epsi.b3.polish.beans.UserAccount;
import epsi.b3.polish.utils.DBUtils;
import epsi.b3.polish.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = { "/result" })
public class ResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ResultServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("Session : " + session);
        // Vérifiez si l'utilisateur s'est connecté (login) ou pas.
        UserAccount loginedUser = MyUtils.getLoginedUser(session);

        // Pas connecté (login).
        if (loginedUser == null) {
            // Redirect (Réorenter) vers la page de connexion.
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        // Enregistrez des informations à l'attribut de la demande avant de forward (transmettre).
        request.setAttribute("user", loginedUser);
        Connection conn = MyUtils.getStoredConnection(request);
        ArrayList<Score> scores = new ArrayList<>();
        try {
            scores = DBUtils.findBestScores(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("scores", scores);

        int actualScore = 0;
        try {
            actualScore = DBUtils.LatestScore(conn, loginedUser.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("actualScore", actualScore);

        // Si l'utilisateur s'est connecté, forward (transmettre) vers la page
        // /WEB-INF/views/userInfoView.jsp
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/resultView.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}