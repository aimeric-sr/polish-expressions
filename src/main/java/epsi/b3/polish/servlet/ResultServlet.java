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

/**
 * Affiche la page de résultat.
 */
@WebServlet(urlPatterns = {"/result"})
public class ResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ResultServlet() {
        super();
    }

    /**
     * Affiche la page de résultat.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // On vérifie si l'utilisateur s'est connecté ou pas.
        UserAccount loginedUser = MyUtils.getLoginedUser(session);

        // Si on n'est pas connecté.
        if (loginedUser == null) {
            // Redirection vers la page home.
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        Connection conn = MyUtils.getStoredConnection(request);
        // On récupère les dix meilleurs scores
        ArrayList<Score> scores = new ArrayList<>();
        try {
            scores = DBUtils.findBestScores(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("scores", scores);

        int actualScore = 0;
        // On récupère le score que vient de faire le joueur.
        try {
            actualScore = DBUtils.LatestScore(conn, loginedUser.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("actualScore", actualScore);

        // Si l'utilisateur s'est connecté, on affiche la page result
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/resultView.jsp");
        dispatcher.forward(request, response);

    }
}