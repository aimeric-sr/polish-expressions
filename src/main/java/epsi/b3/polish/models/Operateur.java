package epsi.b3.polish.models;

public class Operateur {
    public boolean isBinaire(String op) {
        if (op.charAt(0)=='+' || op.charAt(0)=='-' || op.charAt(0)=='*' || op.charAt(0)=='/'){
            return true;
        }
        return false;
    }

    public boolean isUnaire(String op) {
        if (op.charAt(0)=='+' || op.charAt(0)=='-' || op.charAt(0)=='*' || op.charAt(0)=='/'){
            return false;
        }
        return true;
    }

    public float addValues(float value1, float value2) {
        return value1 + value2;
    }

    public float subValues(float value1, float value2) {
        return value1 - value2;
    }

    public float multiplyValues(float value1, float value2) {
        return value1 * value2;
    }

    public float divideValues(float value1, float value2) {
        return value1 / value2;
    }

    public float inverse(float value) {
        return 1 / value;
    }

    public float square(float value) {
        return (float) Math.sqrt(value);
    }
}
