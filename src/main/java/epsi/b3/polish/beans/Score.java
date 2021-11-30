package epsi.b3.polish.beans;

public class Score {

    private int score;
    private String userName;

    public Score() {
    }

    public Score(int score, String userName) {
        this.score = score;
        this.userName = userName;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}