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

@WebServlet(urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ArrayList<Expression> listExpressions = new ArrayList<>();


    public GameServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        listExpressions = new ArrayList<>();
        HttpSession session = request.getSession();
        System.out.println("Session : " + session);
        // Vérifiez si l'utilisateur s'est connecté (login) ou pas.
        UserAccount loginedUser = MyUtils.getLoginedUser(session);

        // Pas connecté (login).
        if (loginedUser == null) {
            // Redirect (Réorenter) vers la page de connexion.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Enregistrez des informations à l'attribut de la demande avant de forward (transmettre).
        request.setAttribute("user", loginedUser);
        Connection conn = MyUtils.getStoredConnection(request);
        ArrayList<Integer> myScores = new ArrayList<>();
        try {
            myScores = DBUtils.findMyScores(conn, loginedUser.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; ++i) {
            Expression expression = new Expression();
            listExpressions.add(expression);
            System.out.println(listExpressions.get(i).getResultExpression());
        }

        request.setAttribute("listExpressions", listExpressions);
        request.setAttribute("myScores", myScores);
        // Si l'utilisateur s'est connecté, forward (transmettre) vers la page
        // /WEB-INF/views/userInfoView.jsp
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/gameView.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean hasError = false;
        String errorString = null;
        HttpSession session = request.getSession();
        UserAccount loginedUser = MyUtils.getLoginedUser(session);
        String[] selectedAnswers = {"", "", "", "", "", "", "", "", "", ""};
        int score = 0;
        String answer = "";
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
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            Score totalScore = new Score(score, loginedUser.getUserName());
            try {
                DBUtils.insertProduct(conn, totalScore);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/result");
        }
    }

}