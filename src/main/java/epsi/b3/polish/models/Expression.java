package epsi.b3.polish.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Character.isDigit;
import static java.lang.Double.parseDouble;
import static java.lang.Math.sqrt;

public class Expression {

    private String randomExpression;
    private String resultExpression;

    public Expression(){
        this.randomExpression = generateRandomExpression();
        this.resultExpression = calculateExpression(this.randomExpression);
    }

    public String getRandomExpression() {
        return randomExpression;
    }

    public String getResultExpression() {
        return resultExpression;
    }

    public String calculateExpression(String expression) {
        Stack<String> calculatedExpression = new Stack<>();
        String number = "";
        for (int i = 0; i < expression.length(); ++i) {
            if (isDigit(expression.charAt(i)) || expression.charAt(i) == '.' || expression.charAt(i) == ',' || expression.charAt(i) == '-' && i == 0) {
                if (expression.charAt(i) == ',') {
                    number = number + '.';

                } else {
                    number = number + expression.charAt(i);
                }
            } else if (expression.charAt(i) == ' ') {
                if (number != "") {
                    calculatedExpression.push(number);
                    number = "";
                }
            } else if (expression.charAt(i) != 'n' && expression.charAt(i) != 'v'
                    && expression.charAt(i) != 'a' && expression.charAt(i) != 'c') {
                double x1 = parseDouble(calculatedExpression.peek());
                calculatedExpression.pop();
                double x2 = 0;
                if (expression.charAt(i) != 'i' && expression.charAt(i) != 'r') {
                    x2 = parseDouble(calculatedExpression.peek());
                    calculatedExpression.pop();
                }
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
                        if(x1 < 0) return "error";
                        calculatedExpression.push(Double.toString(sqrt(x1)));
                        break;
                    case 'i':
                        calculatedExpression.push(Double.toString(1 / x1));
                        break;
                }
            }
            if (i == expression.length() - 1 && number != "") calculatedExpression.push(number);
        }
        double resultDouble = Math.round(parseDouble(calculatedExpression.peek()) * 100.0) / 100.0;
        if ((resultDouble - Math.floor(resultDouble)) == 0.0f) {
            return (int) resultDouble + "" ;
        }
        return resultDouble + "";
    }

    public String generateRandomExpression() {
        String randomExpression = "";
        int nbBinaire = 0;
        int nbNombre = 0;
        int expressionLength = (int) (Math.random() * (10 - 3) + 3);
        int half = expressionLength / 2;
        int indexUnaire = (int) (Math.random() * (half + 1 - 1) + 1);
        ArrayList unaireList = new ArrayList<>(Arrays.asList("inv", "rac"));
        ArrayList binaireList = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
        for (int i = 0; i < expressionLength; ++i) {
            boolean decimal = (int) (Math.random() * (100 - 0) + 0) > 90 ? true : false;
            if (i == 0) {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                if (decimal) {
                    randomExpression = randomExpression + df.format((float) (Math.random() * (100 - 0) + 0));
                } else randomExpression = randomExpression + (int) (Math.random() * (100 - 0) + 0);
                nbNombre++;
            } else if (expressionLength % 2 != 0) {
                if (i == 1) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    if (decimal) {
                        randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                    } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                    nbNombre++;
                } else if (half + 1 == nbNombre) {
                    int index = (int) (Math.random() * (4 - 0) + 0);
                    randomExpression = randomExpression + " " + binaireList.get(index);
                    nbBinaire++;
                } else if (nbNombre > nbBinaire + 1) {
                    boolean operation = (int) (Math.random() * (100 - 0) + 0) > 50 ? true : false;
                    if (operation) {
                        int index = (int) (Math.random() * (2 - 0) + 0);
                        randomExpression = randomExpression + " " + binaireList.get(index);
                        nbBinaire++;
                    } else {
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        if (decimal) {
                            randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                        } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                        nbNombre++;
                    }
                } else {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    if (decimal) {
                        randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                    } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                    nbNombre++;
                }
            } else {
                boolean operation = (int) (Math.random() * (100 - 0) + 0) > 50 ? true : false;
                if (operation && indexUnaire == 1 && indexUnaire - nbBinaire == 1) {
                    int index = (int) (Math.random() * (2 - 0) + 0);
                    randomExpression = randomExpression + " " + unaireList.get(index);
                    nbBinaire++;
                } else if (half == nbNombre) {
                    if (indexUnaire - nbBinaire == 1) {
                        int index = (int) (Math.random() * (2 - 0) + 0);
                        randomExpression = randomExpression + " " + unaireList.get(index);
                    } else {
                        int index = (int) (Math.random() * (4 - 0) + 0);
                        randomExpression = randomExpression + " " + binaireList.get(index);
                    }
                    nbBinaire++;
                } else if (nbNombre > nbBinaire + 1) {
                    operation = (int) (Math.random() * (100 - 0) + 0) > 50 ? true : false;
                    if (operation) {
                        if (indexUnaire - nbBinaire == 1) {
                            int index = (int) (Math.random() * (2 - 0) + 0);
                            randomExpression = randomExpression + " " + unaireList.get(index);
                        } else {
                            int index = (int) (Math.random() * (4 - 0) + 0);
                            randomExpression = randomExpression + " " + binaireList.get(index);
                        }
                        nbBinaire++;
                    } else {
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        if (decimal) {
                            randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                        } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                        nbNombre++;
                    }
                } else {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    if (decimal) {
                        randomExpression = randomExpression + " " + df.format((float) (Math.random() * (100 - 0) + 0));
                    } else randomExpression = randomExpression + " " + (int) (Math.random() * (100 - 0) + 0);
                    nbNombre++;
                }
            }
        }
        return randomExpression;
    }

}
