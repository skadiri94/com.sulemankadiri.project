import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SudokuDriver extends JFrame
{

    public static void main(String[] args) {
        new SudokuDriver();

    }
    public SudokuDriver(){

       // Sudoku sd = new Sudoku();
        super("Sudoku");
        //fileStorage = new File("Progress.dat");
        // player = new Player();
        setSize(500,500);
        //setLocation(500,200);
        setResizable(false);

        Container pane = getContentPane();
        //new Grid();
        //new gPanel(10);

        pane.add(new gPanel(10));

        pane.setVisible(true);


        //String s = JOptionPane.showInputDialog(null ,"Enter anything");

       //sd.fillSudoku();

        //sd.removeMNum(20);
       // System.out.println(sd.toString());


    }
}
