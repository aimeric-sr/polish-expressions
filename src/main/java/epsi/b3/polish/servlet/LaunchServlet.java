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
 * Affiche et permet des actions sur la page de lancement de parti.
 */
@WebServlet(urlPatterns = {"/start"})
public class LaunchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LaunchServlet() {
        super();
    }

    /**
     * Permet l'affichage de la page de jeu.
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
        // Vérifiez si l'utilisateur s'est connecté ou pas.
        UserAccount loginedUser = MyUtils.getLoginedUser(session);

        // Si on n'est pas connecté.
        if (loginedUser == null) {
            // Redirection vers la page de connexion.
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        Connection conn = MyUtils.getStoredConnection(request);
        // On récupère les dix meilleurs scores.
        ArrayList<Score> scores = new ArrayList<>();
        try {
            scores = DBUtils.findBestScores(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("scores", scores);

        // Si l'utilisateur s'est connecté, on affiche la page
        RequestDispatcher dispatcher
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/launchView.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Redirige vers la page de lancement de partie lors d'un clic sur certains boutons.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}