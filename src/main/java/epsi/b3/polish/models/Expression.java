package epsi.b3.polish.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Character.isDigit;
import static java.lang.Double.parseDouble;
import static java.lang.Math.sqrt;

/**
 * Permet de créer une polish expression.
 */
public class Expression {

    private String randomExpression;
    private String resultExpression;

    /**
     * Constructeur permettant de créer une polish expression aléatoire.
     */
    public Expression() {
        this.randomExpression = generateRandomExpression();
        this.resultExpression = calculateExpression(this.randomExpression);
    }

    /**
     * Permet de récupérer l'expression aléatoire.
     *
     * @return une expression aléatoire
     */
    public String getRandomExpression() {
        return randomExpression;
    }

    /**
     * Permet de récupérer le résultat de l'expression aléatoire.
     *
     * @return le résultat de l'expression aléatoire
     */
    public String getResultExpression() {
        return resultExpression;
    }

    /**
     * Génère le résultat de l'expression aléatoire.
     *
     * @param expression une expression aléatoire.
     * @return le résultat de l'expression aléatoire
     */
    public String calculateExpression(String expression) {
        // Création d'une pile.
        Stack<String> calculatedExpression = new Stack<>();
        String number = "";
        // On parcourt l'expression en paramètres, caractères par caractères.
        for (int i = 0; i < expression.length(); ++i) {
            // On vérifie si le caractère est un chiffre, une virgule ou un point.
            if (isDigit(expression.charAt(i)) || expression.charAt(i) == '.' || expression.charAt(i) == ',' || expression.charAt(i) == '-' && i == 0) {
                if (expression.charAt(i) == ',') {
                    // On ajoute un point à notre nombre.
                    number = number + '.';
                } else {
                    // On ajoute le chiffre à notre nombre.
                    number = number + expression.charAt(i);
                }
            }
            // Si le caractère est un espace, on va ajouter le nombre à la pile, s'il n'est pas nul.
            else if (expression.charAt(i) == ' ') {
                if (number != "") {
                    calculatedExpression.push(number);
                    number = "";
                }
            }
            // Si le caractère est différent de n, v, a ou c (à cause du inv et rac) on va faire nos calculs.
            else if (expression.charAt(i) != 'n' && expression.charAt(i) != 'v'
                    && expression.charAt(i) != 'a' && expression.charAt(i) != 'c') {
                // On récupère le nombre au dessus de la pile,convertie en double,
                // pour avoir des nombres décimaux et on le supprime de la pile.
                double x1 = parseDouble(calculatedExpression.peek());
                calculatedExpression.pop();
                double x2 = 0;
                // Si les opérateurs sont binaires on fait de meme qu'avec le premier
                // nombre de la pile.
                if (expression.charAt(i) != 'i' && expression.charAt(i) != 'r') {
                    x2 = parseDouble(calculatedExpression.peek());
                    calculatedExpression.pop();
                }
                // On vérifie si le caractère est un +, un -, un *, un / un inv ou un rac
                // et on effectue le calcul en conséquence, en ajoutant le résultat dans la pile.
                switch (expression.charAt(i)) {
                    case '+':
                        calculatedExpression.push(Double.toString(x1 + x2));
                        break;
                    case '-':
                        calculatedExpression.push(Double.toString(x2 - x1));
                        break;
                    case '*':
                        calculatedExpression.push(Double.toString(x1 * x2));
                        break;
                    case '/':
                        calculatedExpression.push(Double.toString(x2 / x1));
                        break;
                    case 'r':
                        if (x1 < 0) return "error";
                        calculatedExpression.push(Double.toString(sqrt(x1)));
                        break;
                    case 'i':
                        calculatedExpression.push(Double.toString(1 / x1));
                        break;
                }
            }
            // Si la longueur de l'expression est de 1 on va pouvoir retourner directement le nombre
            // (utile pour la phase de test)
            if (i == expression.length() - 1 && number != "") calculatedExpression.push(number);
        }
        // Si le résultat est un entier on renvoie un entier, sinon on renvoie
        // le résultat avec deux chiffres après la virgule.
        double resultDouble = Math.round(parseDouble(calculatedExpression.peek()) * 100.0) / 100.0;
        if ((resultDouble - Math.floor(resultDouble)) == 0.0f) {
            return (int) resultDouble + "";
        }
        return resultDouble + "";
    }

    /**
     * Permet de de générer une polish expression aléatoire, entre 3 et 9 caractères.
     *
     * @return une expression aléatoire
     */
    public String generateRandomExpression() {
        // Déclaration de variables.
        String randomExpression = "";
        int nbBinaire = 0;
        int nbNombre = 0;
        // On récupère un chiffre aléatoire entre 3 et 9,
        // déterminant la longueur de l'expression.
        int expressionLength = (int) (Math.random() * (10 - 3) + 3);
        int half = expressionLength / 2;
        // On décide de l'index où sera positionné l'opérateur unaire, parmi les opérateurs.
        int indexUnaire = (int) (Math.random() * (half + 1 - 1) + 1);
        // On initialise deux listes pour les opérateurs unaires et binaires.
        ArrayList unaireList = new ArrayList<>(Arrays.asList("inv", "rac"));
        ArrayList binaireList = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
        DecimalFormat df = new DecimalFormat();
        // On fait une boucle de la longueur de l'expression que l'on souhaite afficher.
        for (int i = 0; i < expressionLength; ++i) {
            // On décide si le chiffre va avoir des décimales ou non (1 chance sur 9).
            boolean decimal = (int) (Math.random() * (10 - 0) + 0) > 9 ? true : false;
            // Pour le premier tour, le premier élément de notre expression doit être un chiffre ou un nombre.
            if (i == 0) {
                df.setMaximumFractionDigits(2);
                if (decimal) {
                    randomExpression = randomExpression + df.format((float) (Math.random() * (100 - 0) + 0));
                } else randomExpression = randomExpression + (int) (Math.random() * (100 - 0) + 0);
                nbNombre++;
            }
            // Si la longueur de l'expression est impaire, on sait qu'il n'y aura pas d'opérateurs unaires.
            else if (expressionLength % 2 != 0) {
                // Le deuxième élément de l'expression doit être un chiffre ou un nombre aussi,
                // car pour les opérateurs binaires, il en faut deux minimums.
                if (i == 1) {
                    df.setMaximumFractionDigits(2);
                    if (decimal) {
                        randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                    } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                    nbNombre++;
                }
                // On vérifie si le nombre de chiffres/nombres présent dans l'expression est égale à la
                // moitié plus un de la longueur de l'expression, car nous avons remarqué que dans une expression
                // impaire, il y a la moitié + 1 de nombres et la moitié d'opérateurs binaires.
                // Dans le cas ou cette vérification est bonne, on ajoute sans réfléchir des opérateurs binaires aléatoires.
                else if (half + 1 == nbNombre) {
                    int index = (int) (Math.random() * (4 - 0) + 0);
                    randomExpression = randomExpression + " " + binaireList.get(index);
                    nbBinaire++;
                }
                //On vérifie si le nombre de chiffres/nombres dans l'expression est plus grand
                // que le nombre d'opérateurs binaires dans l'expression, si l'on ajoute notre
                // opérateur.
                else if (nbNombre > nbBinaire + 1) {
                    // On a une chance sur deux d'avoir un opérateur.
                    boolean operation = (int) (Math.random() * (100 - 0) + 0) > 50 ? true : false;
                    if (operation) {
                        int index = (int) (Math.random() * (2 - 0) + 0);
                        randomExpression = randomExpression + " " + binaireList.get(index);
                        nbBinaire++;
                    } else {
                        df.setMaximumFractionDigits(2);
                        if (decimal) {
                            randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                        } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                        nbNombre++;
                    }
                }
                // Autrement on ajoute un nombre.
                else {
                    df.setMaximumFractionDigits(2);
                    if (decimal) {
                        randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                    } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                    nbNombre++;
                }
            }
            // Si la longueur de l'expression est paire, on sait qu'il y aura un opérateur unaire.
            else {
                // On a une chance sur deux d'avoir un opérateur.
                boolean operation = (int) (Math.random() * (100 - 0) + 0) > 50 ? true : false;
                // On vérifie si l'on peut ajouter maintenant l'opérateur unaire.
                if (operation && indexUnaire == 1 && indexUnaire - nbBinaire == 1) {
                    int index = (int) (Math.random() * (2 - 0) + 0);
                    randomExpression = randomExpression + " " + unaireList.get(index);
                    nbBinaire++;
                }
                // On vérifie si le nombre de chiffres/nombres présent dans l'expression est égale à la
                // moitié de la longueur de l'expression, car nous avons remarqué que dans une expression
                // paire, il y a la moitié de nombres et la moitié d'opérateurs binaires - 1 plus un opérateur unaire.
                // Dans le cas ou cette vérification est bonne, on ajoute sans réfléchir des opérateurs binaires aléatoires.
                else if (half == nbNombre) {
                    // On vérifie si c'est la position déterminée de l'opérateur unaire.
                    if (indexUnaire - nbBinaire == 1) {
                        int index = (int) (Math.random() * (2 - 0) + 0);
                        randomExpression = randomExpression + " " + unaireList.get(index);
                    }
                    // Autrement on ajoute un opérateur binaire.
                    else {
                        int index = (int) (Math.random() * (4 - 0) + 0);
                        randomExpression = randomExpression + " " + binaireList.get(index);
                    }
                    nbBinaire++;
                }
                //On vérifie si le nombre de chiffres/nombres dans l'expression est plus grand
                // que le nombre d'opérateurs binaires dans l'expression, si l'on ajoute notre
                // opérateur.
                else if (nbNombre > nbBinaire + 1) {
                    operation = (int) (Math.random() * (100 - 0) + 0) > 50 ? true : false;
                    if (operation) {
                        // On vérifie si c'est la position déterminée de l'opérateur unaire.
                        if (indexUnaire - nbBinaire == 1) {
                            int index = (int) (Math.random() * (2 - 0) + 0);
                            randomExpression = randomExpression + " " + unaireList.get(index);
                        }
                        // Autrement on ajoute un opérateur binaire.
                        else {
                            int index = (int) (Math.random() * (4 - 0) + 0);
                            randomExpression = randomExpression + " " + binaireList.get(index);
                        }
                        nbBinaire++;
                    }
                    // Autrement on ajoute un nombre.
                    else {
                        df.setMaximumFractionDigits(2);
                        if (decimal) {
                            randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                        } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                        nbNombre++;
                    }
                }
                // Autrement on ajoute un nombre.
                else {
                    df.setMaximumFractionDigits(2);
                    if (decimal) {
                        randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                    } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                    nbNombre++;
                }
            }
        }
        // On renvoie l'expression générée aléatoirement.
        return randomExpression;
    }

}
