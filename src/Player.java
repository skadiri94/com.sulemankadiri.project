import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String name;
    private ArrayList<String> score = new ArrayList<>();

    public Player() {

    }



    public Player(String name) {
        setName(name);
        setScore(score);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getScore() {
        return score;
    }

    public void setScore(ArrayList<String> score) {
        this.score = score;
    }


    public String toString() {
        return String.format("Player Name: %s\nGames Completed: %d", getName(), getScore());
    }
}
