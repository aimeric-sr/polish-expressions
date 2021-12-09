package epsi.b3.polish.beans;

/**
 * Représente un joueur connecté.
 */
public class UserAccount {

    private String userName;
    private String password;

    /**
     * Constructeur vide permettant de créer joueur sans paramètres.
     */
    public UserAccount() {
    }

    /**
     * Fonction permettant de récupérer le nom d'utilisateur d'un joueur.
     *
     * @return le nom du joueur
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Fonction permettant d'attribuer un nom d'utilisateur à un joueur.
     *
     * @param userName le nom du joueur
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Fonction permettant de récupérer le mot de passe d'un joueur.
     *
     * @return le mot de passe du joueur
     */
    public String getPassword() {
        return password;
    }

    /**
     * Fonction permettant d'attribuer un mot de passe à un joueur.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}