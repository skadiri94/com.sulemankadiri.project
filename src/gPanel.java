import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import static java.lang.String.valueOf;

public class gPanel extends JPanel {


    /**
     * Creating the grid game panel that also implements action listener that takes in the
     * the digits through a gui interface
     **/

//The UI Properties
    private int cellSize;
    private int panelWidth;
    private int panelHeight;
    private final Color OPEN_CELL = new Color(245, 240, 240);
    private final Color CLOSED_CELL = new Color(140, 140, 140);
    private final Color CORRECT = new Color(100, 180, 100);
    private Color WRONG = new Color(200, 0, 50);
    private final Font FONT_SIZE = new Font("Arial", Font.BOLD, 16);
    //private Sudoku puzzle = new Sudoku();
    private JFormattedTextField inputFields[][] = new JFormattedTextField[9][9];
    int[][]finalPuzzle;
    int [][]temp;

        /*
        JPanel overallP = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel eastPanel = new JPanel();

        JButton btnCheck = new JButton("Check");
            JPanel gPanel = new JPanel();

*/

    public gPanel() {
        setCellSize(40);
        setPanelWidth(getCellSize() * inputFields.length);
        setPanelHeight(getCellSize() * inputFields.length);
        setLayout(new GridLayout(inputFields.length, inputFields.length));
        Sudoku puzzle = new Sudoku();
        puzzle.fillSudoku();//Generates complete puzzle.
        //creates a variable to store final puzzle
        finalPuzzle = puzzle.getPuzzle();
        System.out.print(tooString(finalPuzzle));
        ///puzzle.removeMNum(20);//Removing missing digits to create an unsolved puzzle
        //puzzle.removeMNum(40);
        //System.out.print("Hello Man");
        //System.out.print(puzzle.toString());
        temp = geetSudoku(finalPuzzle);
        reemoveMNum(20, temp);//saving the unsolved variable to temp
        /** Allocating a listener to all the input Fields**/

        InputListener listener = new InputListener();
        //Constructing the inputCells 9*9

        for (int row = 0; row < inputFields.length; ++row) {
            for (int col = 0; col < inputFields[row].length; ++col) {

                /**Was trying to get each cell of the sudoko box restricted for only integer and a single value input
                 * had problems with the java document hence i used the JFormattedTextField
                 * https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
                 */

               // inputFields[row][col] = null;
                //inputFields[row][col] = new JFormattedTextField();
                try {
                    inputFields[row][col] = new JFormattedTextField(
                            new MaskFormatter("#"));
                    inputFields[row][col].setColumns(1);//Sets the number of inputs allowed in a cell
                    inputFields[row][col].setFocusLostBehavior( JFormattedTextField.COMMIT );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Assigning the temp variable to grids
                inputFields[row][col].setValue((temp[row][col]));

                if (Integer.parseInt(inputFields[row][col].getText()) != 0){
                    inputFields[row][col].setText(valueOf(temp[row][col]));
                    inputFields[row][col].setEditable(false);

                }
                else {

                    inputFields[row][col].setValue(null);
                    inputFields[row][col].setEditable(true);
                    inputFields[row][col].addActionListener(listener);


                }

                inputFields[row][col].setHorizontalAlignment(JFormattedTextField.CENTER);//Aligns the input to the center
                inputFields[row][col].setFont(FONT_SIZE);

                add(inputFields[row][col]);
            }
        }


        setVisible(true);

    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setPanelWidth(int panelWidth) {
        this.panelWidth = panelWidth;
    }

    public void setPanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }


    private class InputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // All the 9*9 JFormattedTextField invoke this handler. We need to determine
            // which JFormattedTextField (which row and column) is the source for this invocation.
            int selectedRow = -1;
            int selectedCol = -1;
            int input;

            // Getting the source object that fired the event
            JFormattedTextField source = (JFormattedTextField)e.getSource();
            // Scan JFormattedTextField for all rows and columns, and match with the source object
            boolean found = false;
            for (int row = 0; row < inputFields.length && !found; ++row) {
                for (int col = 0; col < inputFields[row].length && !found; ++col) {
                    if (inputFields[row][col] == source) {
                        selectedRow = row;
                        selectedCol = col;
                        found = true;  // break the inner/outer loops
                    }
                }
            }

            /*
             *
             * 1. Get the input String via tfCells[rowSelected][colSelected].getText()
             * 2. Convert the String to int via Integer.parseInt().
             * 3. Assume that the solution is unique. Compare the input number with
             *    the number in the puzzle[rowSelected][colSelected].  If they are the same,
             *    set the background to green (Color.GREEN); otherwise, set to red (Color.RED).
             */

            input = Integer.parseInt(inputFields[selectedRow][selectedCol].getText());
            //System.out.print(input);
            //if(isMatch(finalPuzzle,input))
            if(input == finalPuzzle[selectedRow][selectedCol] ){
                inputFields[selectedRow][selectedCol].setBackground(CORRECT);
                System.out.print(input + " Matches" + finalPuzzle[selectedRow][selectedCol] );
            }

            else
                inputFields[selectedRow][selectedCol].setBackground(WRONG);
                /*System.out.println(selectedRow +" "+ selectedCol);
                System.out.print(tooString(finalPuzzle));
                System.out.print(finalPuzzle[selectedRow][selectedCol]);

                else
                    System.out.print("false");*/


            /*
             * [TODO 6] Check if the player has solved the puzzle after this move.
             * You could update the masks[][] on correct guess, and check the masks[][] if
             * any input cell pending.
             */

        }
    }

    public boolean isMatch(int [][] puzzle, int num){

        for(int row = 0; row<puzzle.length;row++)
            for(int col = 0; col<puzzle[row].length; col++)
                if(puzzle[row][col] == num)
                    return true;

        return false;

    }

    public String tooString(int [][] finalPuzzle) {
        String digits = "";
        for (int i = 0; i<finalPuzzle.length; i++)
        {
            for (int j = 0; j<finalPuzzle[i].length; j++){
                digits += finalPuzzle[i][j] + " ";

            }
            digits +=  "\n";
        }


        return digits;
    }
    public int[][] geetSudoku(int [][] puzzle){
        int [][] num = new int[9][9];

        for(int i=0;i<puzzle.length;i++){
            for(int j=0;j<puzzle[i].length;j++){
                num[i][j] = puzzle[i][j];
            }
        }

        return num;
    }

    public void reemoveMNum(int mNum, int[][] puzzle)
    {
        int count = mNum;
       // while
        while (count != 0)
        {
            int cellIndex = ((int) (Math.random()*(81 - 1 + 1))) + 1;


            //System.out.println(cellIndex);
            // extract coordinates i  and j
            int i = (cellIndex/9);
            int j = cellIndex%9;

            if (j != 0)
                j = j - 1;

            // System.out.println(i+" "+j);
            if (puzzle[i][j] != 0)
            {
                count--;
                puzzle[i][j] = 0;
            }
        }
    }
}

    /**
         * I had problems with the 2D array trying to get the reference instead of the contents
         * I  did research on 2D arrays on http://math.hws.edu/javanotes/c7/s5.html and got to resolve
         * the problem.


        public int[][] getDigits() {
            //btnCheck.addActionListener(e ->
            int[][] allNums = new int[9][9];

            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    allNums[i][j] = Integer.parseInt(textFields[i][j].getText());
            System.out.println(allNums.toString());
            return allNums;

            //});
        }

        public void fillGrid(JTextField[][] textFields) {
            int[][] digits = new int[9][9];

            for (int i = 0; i < digits.length; i++) {
                for (int j = 0; j < digits.length; j++) {
                    //while(!textFields[i][j].getText().equals("")) {
                    //digits[i][j] = (int) (Math.random() * ((9 - 1) + 1)) + 1;
                    digits[i][j] = 0;
                    //digits[i][j] = Integer.parseInt(textFields[i][j].getText());
                    textFields[i][j].setText(String.valueOf(digits[i][j]));
                    if(Integer.parseInt(textFields[i][j].getText()) == 0){
                        textFields[i][j].setText(String.valueOf(""));
                    }

                }
					/*do{
						textFields[i][j].setText(String.valueOf(digits[i][j]));
					}while(textFields != null);
                //digits[i][j] = (int) (Math.random() * ((9 - 1) + 1)) + 1;


            }
        }
        // Remove the K no. of digits to
        // complete game
        public void addSDigits()
        {


            int count = 20;
            while (count != 0)
            {
                int cellId = randomGenerator(9*9);
                System.out.print(cellId);

                // System.out.println(cellId);
                // extract coordinates i  and j
                int i = (9-cellId);
                int j = cellId%9;
                if (j == 0)
                    j = j + 1;

                System.out.println(i+" "+j);
                if (Integer.parseInt(textFields[i][j].getText()) == 0)
                {
                    count--;
                    int digits = (int) (Math.random() * ((9 - 1) + 1)) + 1;
                    textFields[i][j].setText(String.valueOf(digits));
                }
            }

        }

        int randomGenerator(int num)
        {
            return (int) Math.floor((Math.random()*num+1));
        }


        private class BtnCheck implements ActionListener{
            public void actionPerformed(ActionEvent e) {
                fillGrid(textFields);
                addSDigits();
                System.out.println("Welcome");
            } // end actionPerformed
        } // end CancelButtonHandler inner class


    }

**/