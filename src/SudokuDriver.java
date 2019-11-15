public class SudokuDriver {

    public static void main(String[] args) {

        Sudoku sd = new Sudoku();
        new gPanel();


        //String s = JOptionPane.showInputDialog(null ,"Enter anything");

       sd.fillSudoku();

        //sd.removeMNum(20);
       // System.out.println(sd.toString());


    }
}
