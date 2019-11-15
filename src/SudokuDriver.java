public class SudokuDriver {

    public static void main(String[] args) {

        Sudoku sd = new Sudoku();


        //String s = JOptionPane.showInputDialog(null ,"Enter anything");

       sd.fillSudoku();
        System.out.println(sd.toString());


    }
}
