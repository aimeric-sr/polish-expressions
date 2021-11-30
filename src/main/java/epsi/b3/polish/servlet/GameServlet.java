package epsi.b3.polish.servlet;

import epsi.b3.polish.beans.Score;
import epsi.b3.polish.beans.UserAccount;
import epsi.b3.polish.models.Expression;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Affiche et permet des actions sur la page de jeu.
 */
@WebServlet(urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ArrayList<Expression> listExpressions = new ArrayList<>();


    public GameServlet() {
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
        listExpressions = new ArrayList<>();
        HttpSession session = request.getSession();
        // Vérifiez si l'utilisateur s'est connecté ou pas.
        UserAccount loginedUser = MyUtils.getLoginedUser(session);

        // Si on n'est pas connecté.
        if (loginedUser == null) {
            // Redirection vers la page de connexion.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Enregistrez des informations à l'attribut de la demande avant de rediriger.
        request.setAttribute("user", loginedUser);
        Connection conn = MyUtils.getStoredConnection(request);
        ArrayList<Integer> myScores = new ArrayList<>();
        // On récupère les meilleurs scores du joueur.
        try {
            myScores = DBUtils.findMyScores(conn, loginedUser.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // On génère dix expressions aléatoires.
        for (int i = 0; i < 10; ++i) {
            Expression expression = new Expression();
            listExpressions.add(expression);
        }

        request.setAttribute("listExpressions", listExpressions);
        request.setAttribute("myScores", myScores);
        // Si l'utilisateur s'est connecté, redirigé vers la page de jeu.
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/gameView.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Permet la redirection vers la page result si tous les champs
     * sont remplis avec des valuers corrects.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Déclaration de variables.
        boolean hasError = false;
        String errorString = null;
        HttpSession session = request.getSession();
        UserAccount loginedUser = MyUtils.getLoginedUser(session);
        String[] selectedAnswers = {"", "", "", "", "", "", "", "", "", ""};
        int score = 0;
        String answer = "";
        // Vérifie si les champs sont remplis correctement et on compare avec le résultat attendu.
        for (int i = 0; i < 10; ++i) {
            answer = request.getParameter("calcul" + i);
            if (answer.toLowerCase(Locale.ROOT).equals("impossible")) selectedAnswers[i] = answer;
            if (listExpressions.get(i).getResultExpression().equals("error") &&
                    answer.toLowerCase(Locale.ROOT).equals("impossible")) score++;
            else {
                if (answer.contains(",")) {
                    try {
                        double d = Double.parseDouble(listExpressions.get(i).getResultExpression());
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        String noDecimal = df.format(d);
                        selectedAnswers[i] = answer;
                        if (noDecimal.equals(answer)) {
                            score++;
                        }
                    } catch (NumberFormatException nfe) {
                        hasError = true;
                        if (answer == "") {
                            errorString = "Veuillez renseigner tous les champs";
                        } else {
                            errorString = "Veuillez renseigner les champs avec des nombres valables";
                        }
                    }
                } else {
                    try {
                        double d = Double.parseDouble(answer);
                        selectedAnswers[i] = answer;
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(0);
                        String noDecimal = df.format(d);
                        if (listExpressions.get(i).getResultExpression().equals(d + "") ||
                                listExpressions.get(i).getResultExpression().equals(noDecimal)) score++;
                    } catch (NumberFormatException nfe) {
                        hasError = true;
                        if (answer == "") {
                            errorString = "Veuillez renseigner tous les champs";
                        } else {
                            errorString = "Veuillez renseigner les champs avec des nombres valables";
                        }
                    }
                }
            }
        }
        // Vérifie s'il y a une erreur.
        if (hasError) {
            Connection conn = MyUtils.getStoredConnection(request);

            ArrayList<Integer> myScores = new ArrayList<>();
            try {
                myScores = DBUtils.findMyScores(conn, loginedUser.getUserName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("errorString", errorString);
            request.setAttribute("listExpressions", listExpressions);
            request.setAttribute("myScores", myScores);
            request.setAttribute("selectedAnswers", selectedAnswers);


            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/gameView.jsp");

            dispatcher.forward(request, response);
        }
        // Ajoute un score et redirige vers la page result.
        else {
            Connection conn = MyUtils.getStoredConnection(request);
            Score totalScore = new Score(score, loginedUser.getUserName());
            try {
                DBUtils.insertScore(conn, totalScore);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/result");
        }
    }

}