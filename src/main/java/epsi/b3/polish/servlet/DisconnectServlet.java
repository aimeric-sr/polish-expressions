package epsi.b3.polish.servlet;

import epsi.b3.polish.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = { "/disconnect" })
public class DisconnectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DisconnectServlet() {
        super();
    }

    // Affichez la page de connexion.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Transmettez vers la page /WEB-INF/views/loginView.jsp
        // (L'utilisateur ne peut pas accéder directement
        // à la page JSP qui se trouve dans le dossier WEB-INF).
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");

        dispatcher.forward(request, response);

    }

    // Lorsque l'utilisateur saisit userName & password, et presse le bouton Submit.
    // Cette méthode sera exécutée.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session = request.getSession();

            MyUtils.storeLoginedUser(session, null);
            MyUtils.deleteUserCookie(response);
            response.sendRedirect(request.getContextPath() + "/home");
    }

}