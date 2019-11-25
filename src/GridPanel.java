import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;

import static java.lang.String.valueOf;

public class GridPanel extends JPanel implements Serializable {

    private final Color OPEN_CELL = new Color(245, 240, 240);
    private final Color CLOSED_CELL = new Color(140, 140, 140);
    private final Color CORRECT = new Color(100, 180, 100);
    private final Color BORDER = new Color(100, 150, 200);
    private final Font FONT_SIZE = new Font("Arial", Font.BOLD, 16);
    int[][] finalPuzzle;
    int[][] temp;
    //The UI Properties
    private int cellSize;
    private int panelWidth;
    private int panelHeight;
    private Sudoku puzzle;
    private JFormattedTextField[][] inputFields = new JFormattedTextField[9][9];
    private Color WRONG = new Color(200, 0, 50);


    public GridPanel(int level) {
        setCellSize(40);
        // setBackground(BORDER);
        setPanelWidth(getCellSize() * inputFields.length);
        setPanelHeight(getCellSize() * inputFields.length);

        GridLayout gl = new GridLayout(inputFields.length, inputFields.length);
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, BORDER));
        setLayout(gl);
        genPuzzle(level);
        paintGrid();

        for (int i = 0; i < inputFields.length; i++) {
            for (int j = 0; j < inputFields[i].length; ++j) {
                add(inputFields[i][j]);
            }
        }
        setVisible(true);
    }

    /**
     * displays a String in a message dialog
     *
     * @param txt The string to be displayed
     */
    public static void showMessage(String txt) {
        JOptionPane.showMessageDialog(null, txt);
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public void setPanelWidth(int panelWidth) {
        this.panelWidth = panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public void setPanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
    }

    public int[][] getFinalPuzzle() {
        return finalPuzzle;
    }

    public void genPuzzle(int level) {

        puzzle = new Sudoku();
        puzzle.fillSudoku();//Generates complete puzzle.
        //creates a variable to store final puzzle
        finalPuzzle = puzzle.getPuzzle();
        System.out.print(toString(finalPuzzle));
        //System.out.print(puzzle.toString());
        temp = getSudoku(finalPuzzle);
        reemoveMNum(level, temp);//saving the unsolved variable to temp

    }

    public boolean isMatch(int[][] puzzle, int num) {

        for (int row = 0; row < puzzle.length; row++)
            for (int col = 0; col < puzzle[row].length; col++)
                if (puzzle[row][col] == num)
                    return true;
        return false;

    }

    public String toString(int[][] finalPuzzle) {
        String digits = "";
        for (int i = 0; i < finalPuzzle.length; i++) {
            for (int j = 0; j < finalPuzzle[i].length; j++) {
                digits += finalPuzzle[i][j] + " ";

            }
            digits += "\n";
        }
        return digits;
    }

    public int[][] getSudoku(int[][] puzzle) {
        int[][] num = new int[9][9];

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                num[i][j] = puzzle[i][j];
            }
        }

        return num;
    }

    /**
     * Method overload returns and a 2 dimension integer arrays
     *
     * @param puzzle is  The puzzle inputed by use which will be  to be displayed
     */

    public int[][] getSudoku(JFormattedTextField[][] puzzle) {
        int[][] num = new int[9][9];
        int numInt;

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                // puzzle[i][j].getValue();
                if (puzzle[i][j].getValue() == "") {
                    numInt = 0;
                    num[i][j] = numInt;
                    System.out.print(puzzle[i][j].getValue());
                }
                num[i][j] = Integer.parseInt(puzzle[i][j].getText());

            }
        }

        return num;
    }

    public int[][] getSudoku() {
        int[][] num = new int[9][9];

        for (int i = 0; i < inputFields.length; i++) {
            for (int j = 0; j < inputFields[i].length; j++) {
                // puzzle[i][j].getValue();
                // int numTemp = Integer.parseInt(inputFields[i][j].getText());
                if (inputFields[i][j].getValue() == null) {
                    num[i][j] = 0;
                    inputFields[i][j].setBackground(WRONG);
                } else
                    num[i][j] = Integer.parseInt(inputFields[i][j].getText());
            }
        }

        return num;
    }

    /**
     * This Method defines and create the Grid cells before they are added to the panel
     * it makes the constructor more tidy as this will require less lines of code going
     * into the constructor.
     **/
    private void paintGrid() {

        for (int row = 0; row < inputFields.length; ++row) {
            for (int col = 0; col < inputFields[row].length; ++col) {

                /**Was trying to get each cell of the sudoko box restricted for only integer and a single value input
                 * had problems with the java document hence i used the JFormattedTextField
                 * https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
                 * it's a requirement between the JFormattedTextField, NumberFormatter and NumberFormat that ""
                 * is not a valid number, therefore it's rejecting your attempt to remove the last character.
                 * This requirement is been enforced by numberFormatter.setAllowsInvalid. If I really did not  care about the format of the value,
                 * but Just simply restricting the user's input, I would have used the JTextFieldLimit and DocumentFilter Class
                 */
                try {
                    inputFields[row][col] = new JFormattedTextField(
                            new MaskFormatter("#"));
                    inputFields[row][col].setColumns(1);//Sets the number of inputs allowed in a cell
                    inputFields[row][col].setFocusLostBehavior(JFormattedTextField.COMMIT);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Assigning the temp variable to grids
                inputFields[row][col].setValue((temp[row][col]));

                if (Integer.parseInt(inputFields[row][col].getText()) != 0) {
                    inputFields[row][col].setText(valueOf(temp[row][col]));
                    inputFields[row][col].setEditable(false);

                } else {

                    inputFields[row][col].setValue(null);
                    inputFields[row][col].setEditable(true);

                    inputFields[row][col].addKeyListener(new KeyAdapter() {
                        public void keyReleased(KeyEvent e) {
                            int selectedRow = -1;
                            int selectedCol = -1;
                            int input;

                            // Getting the source object that fired the event
                            JFormattedTextField source = (JFormattedTextField) e.getSource();
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
                             * 1. Get the input String via tfCells[rowSelected][colSelected].getText()
                             * 2. Convert the String to int via Integer.parseInt().
                             * 3. Assume that the solution is unique. Compare the input number with
                             *    the number in the puzzle[rowSelected][colSelected].  If they are the same,
                             *    set the background to green (Color.GREEN); otherwise, set to red (Color.RED).
                             */

                            input = Integer.parseInt(inputFields[selectedRow][selectedCol].getText());
                            //System.out.print(input);
                            //if(isMatch(finalPuzzle,input))
                            if (input == finalPuzzle[selectedRow][selectedCol]) {
                                inputFields[selectedRow][selectedCol].setBackground(CORRECT);
                                // System.out.print(input + " Matches" + finalPuzzle[selectedRow][selectedCol] );
                            } else
                                inputFields[selectedRow][selectedCol].setBackground(WRONG);

                        }
                    });


                }

                inputFields[row][col].setHorizontalAlignment(JFormattedTextField.CENTER);//Aligns the input to the center
                inputFields[row][col].setFont(FONT_SIZE);
                // inputFields[row][col].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
                /**This Set borders to the cells giving the illusion of a 3 x 3 Grid**/

                if (col == 3 || col == 6)
                    inputFields[row][col].setBorder(BorderFactory.createMatteBorder(1, 3, 0, 0, BORDER));

                if (row == 3 || row == 6)
                    inputFields[row][col].setBorder(BorderFactory.createMatteBorder(3, 1, 0, 0, BORDER));

                if ((row == 3 && col == 3) || (row == 6 && col == 6) || (row == 6 && col == 3) || (row == 3 && col == 6))
                    inputFields[row][col].setBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, BORDER));

            }
        }
    }

    /**
     * This method proved to be a challinging as my program always crashes once in every 3 or 5 runs giving an Out of
     *
     * @param mNum
     * @param puzzle
     * @IndexOutofbounds Exception because the cellIndex that was being generated randomly was between 1 and 81 and for
     * It seldom generates 81 and when this happens and for i it divides it by 9 to get the cellIndex and and being an array of
     * size 9 the index ends at 8 hence i get an outOfbound exception and to fix this i simply subtract 1 from i when 81 is
     * randomly generated.
     */

    public void reemoveMNum(int mNum, int[][] puzzle) {
        int count = mNum;
        // while
        while (count != 0) {
            int cellIndex = ((int) (Math.random() * (81 - 1 + 1))) + 1;

            //System.out.println(cellIndex);
            // extract coordinates i  and j
            int i = (cellIndex / 9);
            int j = cellIndex % 9;

            if (i == 9)
                i--;

            //if(j==9)
            //    j--;

            //if (j != 0)
            //    j = j - 1;

            //System.out.println(i+" "+j);
            if (puzzle[i][j] != 0) {
                count--;
                puzzle[i][j] = 0;
            }
        }
    }

    public boolean isEqual(int[][] finalPuzzle, int[][] temp) {

        if (finalPuzzle == null) {

            return (temp == null);

        }

        if (temp == null) {

            return false;

        }

        if (finalPuzzle.length != temp.length) {

            return false;

        }

        for (int i = 0; i < finalPuzzle.length; i++) {

            if (!Arrays.equals(finalPuzzle[i], temp[i])) {

                return false;
            }

        }

        return true;
    }

    /*
        public String resultCheck(int[][] finalpuzzle, int[][] temp2) {
            String txt = "";

            if (isEqual(finalpuzzle, temp2))
                txt = "Win!";
            else {
                txt = "Loss!";
            }

            return txt;
        }
    */
    public boolean resultCheck() {

        temp = getSudoku();

        return isEqual(finalPuzzle, temp);
    }

    public void reSetPuzzle() {
        puzzle.fillSudoku();
    }


}
