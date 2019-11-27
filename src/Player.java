import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String name;
    private ArrayList<String> score = new ArrayList<>();


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

    public void addScore(String score) {
        this.score.add(score);
    }

    public String toString() {
        String txt ="";
        for(String scr: score){
            if(scr != null) {
                txt += scr + "\n";
            }
        }
        return String.format("Name: %s\nCompleted:\n %s", getName(), txt);
    }



}
