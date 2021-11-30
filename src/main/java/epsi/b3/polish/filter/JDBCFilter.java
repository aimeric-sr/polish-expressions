package epsi.b3.polish.filter;

import epsi.b3.polish.conn.ConnectionUtils;
import epsi.b3.polish.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

/**
 * Vérifie la requête pour assurer qu'il ouvre uniquement la connexion JDBC pour des requêtes nécessaires,
 * par exemple, pour Servlet, évitez d'ouvrir la connexion JDBC pour les requêtes générales comme image, css, js, html.
 */
@WebFilter(filterName = "jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter {

    public JDBCFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    /**
     * Vérifie que la cible de la requête est une servlet.
     *
     * @param request la requête
     * @return
     */
    private boolean needJDBC(HttpServletRequest request) {
        //
        // Servlet Url-pattern: /spath/*
        //
        // => /spath
        String servletPath = request.getServletPath();
        // => /abc/mnp
        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            // => /spath/*
            urlPattern = servletPath + "/*";
        }

        // Key: servletName.
        // Value: ServletRegistration
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();

        // La collection de tous les servlets dans votre Webapp.
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;

            }
        }
        return false;
    }

    /**
     * Ouvre ou non la connexion JDBC si la cible est bien une servlet.
     *
     * @param request  la requête
     * @param response la réponse
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (this.needJDBC(req)) {

            Connection conn = null;
            try {
                // Crée un objet Connection se connectant à la base de données.
                conn = ConnectionUtils.getConnection();
                conn.setAutoCommit(false);

                // Enregistre l'objet Connection dans l'attribut de la demande.
                MyUtils.storeConnection(request, conn);

                // Allez au filtre suivant ou à la cible.
                chain.doFilter(request, response);

                // Appelle la méthode commit() pour finir la transaction avec DB.
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtils.rollbackQuietly(conn);
                throw new ServletException();
            } finally {
                ConnectionUtils.closeQuietly(conn);
            }
        } else {
            // Allez au filtre suivant ou à la cible.
            chain.doFilter(request, response);
        }

    }

}