package epsi.b3.polish.utils;

import epsi.b3.polish.beans.UserAccount;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

/**
 * Permet de manipuler les données.
 */
public class MyUtils {

    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

    /**
     * Stocke la connexion.
     *
     * @param request
     * @param conn
     */
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }

    /**
     * On récupère la connexion stockée.
     *
     * @param request
     * @return la connexion stockée
     */
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        return conn;
    }

    /**
     * Conserve les informations de l'utilisateur en Session.
     *
     * @param session
     * @param loginedUser
     */
    public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }

    /**
     * Permet d'obtenir les informations de l'utilisateur stockées dans la Session.
     *
     * @param session
     * @return le joueur connecté
     */
    public static UserAccount getLoginedUser(HttpSession session) {
        UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
        return loginedUser;
    }

    /**
     * Stocke les informations de l'utilisateur dans Cookie.
     *
     * @param response
     * @param user     l'utilisateur
     */
    public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
        // 1 jour (converti en secondes)
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    /**
     * Récupère l'utilisateur stocké dans le cookie.
     *
     * @param request
     * @return l'utilisateur stocké dans le cookie
     */
    public static String getUserNameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Supprime les cookies de l'utilisateur.
     *
     * @param response
     */
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        // 0 seconde. (ce cookie expirera immédiatement)
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
}