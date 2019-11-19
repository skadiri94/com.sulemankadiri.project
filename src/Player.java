import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int score;

    public Player(){

    }

    public Player(String name){
        setName(name);
        setScore(score);

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String toString() {
        return String.format("%s%d",getName(),getScore());
    }
}
