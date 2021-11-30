package epsi.b3.polish.servlet;

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

/**
 * Affiche et permet des actions sur la page d'inscription.
 */
@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    /**
     * Affiche la page d'inscription.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Affiche la page de register.
        RequestDispatcher dispatcher
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");

        dispatcher.forward(request, response);

    }

    /**
     * Renvoie vers la page de lancement de partie si les cahnmps saisit sont corrects et crées un
     * nouvel utilsateur si celui-ci n'existe pas.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);

        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;

        if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Vous devez renseigner un nom d'utilisateur et un mot de passe";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
                // On cherche user dans DB.
                user = DBUtils.findUser(conn, userName, password);

                if (user == null) {
                    user = new UserAccount();
                    user.setUserName(userName);
                    user.setPassword(password);
                    DBUtils.insertUser(conn, user);
                } else {
                    hasError = true;
                    errorString = "Cet utilisateur existe déjà";

                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        // On vérifie s'il y a des erreurs
        if (hasError) {
            user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);

            // On enregistre des données dans l'attribut de la demande avant de les transmettre.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);

            // On redirige vers la page register
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");

            dispatcher.forward(request, response);
        }
        // S'il n'y a pas de l'erreur.
        // On enregistre les informations de l'utilisateur dans Session,
        // et on transmet vers la page userInfo.
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);

            // Si l'utilisateur sélectionne la fonction "Remember me".
            if (remember) {
                MyUtils.storeUserCookie(response, user);
            }
            // Si non, on supprime Cookie
            else {
                MyUtils.deleteUserCookie(response);
            }
            // On redirige vers la page start.
            response.sendRedirect(request.getContextPath() + "/start");
        }
    }

}