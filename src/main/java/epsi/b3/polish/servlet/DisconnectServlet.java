package epsi.b3.polish.servlet;

import epsi.b3.polish.utils.MyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Permet la déconnexion lors d'un clic sur le bouton quitter.
 */
@WebServlet(urlPatterns = {"/disconnect"})
public class DisconnectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DisconnectServlet() {
        super();
    }

    /**
     * Lorsque l'utilisateur clique sur le bouton quitter, il est redirigé vers
     * la page home et est déconnecté.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        MyUtils.storeLoginedUser(session, null);
        MyUtils.deleteUserCookie(response);
        response.sendRedirect(request.getContextPath() + "/home");
    }

}