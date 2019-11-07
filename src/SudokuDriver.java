import javax.swing.*;

public class SudokuDriver {

    public static void main(String[] args) {

        Sudoku sd = new Sudoku(9,20);


        //String s = JOptionPane.showInputDialog(null ,"Enter anything");

        sd.fillSudoku();
        //System.out.println(sd.toString());

    }
}
