package epsi.b3.polish.beans;

/**
 * Représente un score d'un joueur.
 *
 * @author Aimeric Sorin
 * @author Etienne Lepetit
 * @version 1.0
 */
public class Score {

    private int score;
    private String userName;

    /**
     * Constructeur vide permettant de créer un score sans paramètres,
     * c'est-à-dire un score nul attribué à aucun joueur.
     */
    public Score() {
    }

    /**
     * Constructeur permettant de créer un score, pour un joueur spécifique.
     *
     * @param score    le score, sur 10
     * @param userName le joueur à qui appartient le score
     */
    public Score(int score, String userName) {
        this.score = score;
        this.userName = userName;
    }

    /**
     * Fonction permettant de récupérer le score d'un joueur.
     *
     * @return le score du joueur
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Fonction permettant d'attribuer un score à un joueur.
     *
     * @param score le score devant être attribué
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Fonction permettant de récupérer le nom du joueur à qui appartient le score.
     *
     * @return le nom du joueur
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Fonction permettant d'attribuer le nom du joueur à qui appartient le score.
     *
     * @param userName le nom du joueur
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}