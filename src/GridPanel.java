import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;

import static java.lang.String.valueOf;

/**
 * the Class Creates a 9x9 Grid Panel with a 3x3 sub Grid and assigns a Sudoku puzzle
 * the Grid initializing some cells(Closed Cells) and Open cells that allows for
 * a user input of only Integer values.
 */
public class GridPanel extends JPanel implements Serializable {

    //The UI Properties
    private final Color CORRECT = new Color(100, 180, 100);
    private final Color BORDER = new Color(100, 150, 200);
    private final Font FONT_SIZE = new Font("Arial", Font.BOLD, 16);
    private final Color WRONG = new Color(200, 0, 50);

    private int[][] temp;
    private int cellSize;
    private int panelWidth;
    private int panelHeight;
    private Sudoku puzzle;
    private int[][] finalPuzzle;
    private JFormattedTextField[][] inputFields = new JFormattedTextField[9][9];


    /**
     * This is the constructor for the GridPanel that creat a grid for the Sudoku  puzzle using a 2D array JFormatted Text Fields
     *
     * @param level gives the number of cells that will be Open for user input in the grid
     **/
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

        for (JFormattedTextField[] inputField : inputFields) {
            for (JFormattedTextField jFormattedTextField : inputField) {
                add(jFormattedTextField);
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

    /**
     * gets the value of Cell Size
     *
     * @return the value of cell size
     **/
    private int getCellSize() {
        return cellSize;
    }

    /**
     * Sets the Value of Cell Size
     *
     * @param cellSize the value to be set
     */
    private void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * gets the value of Panel width
     *
     * @return the value of the Panel width
     */
    public int getPanelWidth() {
        return panelWidth;
    }

    /**
     * Sets the value of Panel Width
     *
     * @param panelWidth the value to be set
     */
    private void setPanelWidth(int panelWidth) {
        this.panelWidth = panelWidth;
    }

    /**
     * gets the value of Panel Height
     *
     * @return the value of Panel Height
     */
    public int getPanelHeight() {
        return panelHeight;
    }

    /**
     * Sets the value of Panel Height
     *
     * @param panelHeight the value to be set
     */
    private void setPanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
    }

    /**
     * gets the value of the Final Puzzle
     *
     * @return the Values
     */
    public int[][] getFinalPuzzle() {
        return finalPuzzle;
    }

    /**
     * generates the Sudoko puzzle, first creates a sudoko object fills sudoko and get the puzzle  and stores
     * it in the finalpuzzle variable and the remove some digits to creat a temp puzzle that will be assigned
     * to the cells of the grid.
     *
     * @param level the number of digits to be removed from the complete puzzle wich is first generated
     */
    private void genPuzzle(int level) {

        puzzle = new Sudoku();
        puzzle.fillSudoku();//Generates complete puzzle.
        //creates a variable to store final puzzle
        finalPuzzle = puzzle.getPuzzle();
        System.out.print(toString(finalPuzzle));
        temp = getSudoku(finalPuzzle);
        removeMNum(level, temp);//saving the unsolved variable to temp

    }

    /**
     * Compares each digits in the 2D array puzzle to an Integer and returns true if it matches
     *
     * @param puzzle the puzzle which is being checked
     * @param num    the integer value being compared to the digits of the puzzle
     * @return a boolean value
     */
    public boolean isMatch(int[][] puzzle, int num) {

        for (int[] ints : puzzle) {
            for (int anInt : ints)
                if (anInt == num)
                    return true;
        }
        return false;
    }

    /**
     * returns a string/textual representation of the 2D array object.
     *
     * @param finalPuzzle the 2D array object to be represented in string
     * @return the String/textual representation
     */
    private String toString(int[][] finalPuzzle) {
        StringBuilder digits = new StringBuilder();
        for (int[] ints : finalPuzzle) {
            for (int j = 0; j < ints.length; j++) {
                digits.append(ints[j]).append(" ");

            }
            digits.append("\n");
        }
        return digits.toString();
    }

    /**
     * takes the value of 2D arrays and reassigns it to another 2D array variable
     *
     * @param puzzle the 2D array to be re-assigned
     * @return the values  that are being re-assigned
     */
    private int[][] getSudoku(int[][] puzzle) {
        int[][] num = new int[9][9];

        for (int i = 0; i < puzzle.length; i++) {
            System.arraycopy(puzzle[i], 0, num[i], 0, puzzle[i].length);
        }

        return num;
    }

    /**
     * takes the value of a 2D JFormattedTextField array and re-assigned them to a 2D integer array variable
     *
     * @return the 2D integer array.
     */
    private int[][] getSudoku() {
        int[][] num = new int[9][9];

        for (int i = 0; i < inputFields.length; i++) {
            for (int j = 0; j < inputFields[i].length; j++) {

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
     * This Method defines, create and designs the Grid cells before they are added to the panel
     * and it also adds ActionListeners to each cell of the grids
     **/
    private void paintGrid() {

        for (int row = 0; row < inputFields.length; ++row) {
            for (int col = 0; col < inputFields[row].length; ++col) {

                /*Was trying to get each cell of the sudoko box restricted for only integer and a single value input
                  had problems with the java document hence i used the JFormattedTextField
                   {@link https://stackoverflow.com/questions/11093326/restricting-JTextfield-input-to-integers}
                  it's a requirement between the JFormattedTextField, NumberFormatter and NumberFormat that ""
                  is not a valid number, therefore it's rejecting your attempt to remove the last character.
                  This requirement is been enforced by numberFormatter.setAllowsInvalid. If I really did not  care about the format of the value,
                  but Just simply restricting the user's input, I would have used the JTextFieldLimit and DocumentFilter Class
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
                    //inputFields[row][col].setBackground(CLOSED_CELL);

                } else {

                    inputFields[row][col].setValue(null);
                    inputFields[row][col].setEditable(true);

                    inputFields[row][col].addKeyListener(new KeyAdapter() {
                        public void keyReleased(KeyEvent e) {
                            int selectedRow = -1;
                            int selectedCol = -1;
                            int input = 0;

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
                              Geting the input String via inputFields[rowSelected][colSelected].getText()
                              and then Converting the String to int via Integer.parseInt().
                              And Checking the input against the final puzzle if the are thesame
                              the background color is set to Green and if wrong the background
                              color is set to Red
                             */

                            boolean valid = false;
                            while (!valid) {

                                try {
                                    input = Integer.parseInt(inputFields[selectedRow][selectedCol].getText());
                                    valid = true;

                                } // end try
                                catch (NumberFormatException f) {
                                    break;
                                } // end catch

                            }

                            if (input == finalPuzzle[selectedRow][selectedCol]) {
                                inputFields[selectedRow][selectedCol].setBackground(CORRECT);

                            } else
                                inputFields[selectedRow][selectedCol].setBackground(WRONG);
                        }
                    });

                }

                inputFields[row][col].setHorizontalAlignment(JFormattedTextField.CENTER);//Aligns the input to the center
                inputFields[row][col].setFont(FONT_SIZE);
                // inputFields[row][col].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
                /*This Set borders to the cells giving the illusion of a 3 x 3 Grid**/

                if (col == 3 || col == 6)
                    inputFields[row][col].setBorder(BorderFactory.createMatteBorder(1, 3, 0, 0, BORDER));

                if (row == 3 || row == 6)
                    inputFields[row][col].setBorder(BorderFactory.createMatteBorder(3, 1, 0, 0, BORDER));

                if ((row == 3 && col == 3) || (row == 6 && col == 6) || (row == 6 && col == 3) || (row == 3 && col == 6))
                    inputFields[row][col].setBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, BORDER));

            }
        }
    }

    /*
     * This method proved to be a challinging as my program always crashes once in every 3 or 5 runs giving an Out of
     * @param mNum si the number of celss that will be removed to form the puzzle.
     * @param puzzle is the complete puzzle that is being generate before some digits are randomly removed.
     * @IndexOutofbounds Exception because the cellIndex that was being generated randomly was between 1 and 81 and for
     * It seldom generates 81 and when this happens and for i it divides it by 9 to get the cellIndex and and being an array of
     * size 9 the index ends at 8 hence i get an outOfbound exception and to fix this i simply subtract 1 from i when 81 is
     * randomly generated.
     */

    /**
     * removes random digits from the final puzzle to creat the incomplete puzzle
     *
     * @param mNum   the number of digits to be removed
     * @param puzzle the complete puzzle the random digits is to be removed from
     */
    private void removeMNum(int mNum, int[][] puzzle) {
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

    /**
     * takes in two 2D arrays puzzle and compares them for equality and returns true of they are equal
     *
     * @param finalPuzzle puzzle A to be compared with B
     * @param temp        puzzle B to be compared with A
     * @return a boolean Value
     */
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

    /**
     * checks results of the puzzle
     *
     * @return a boolean value
     */
    boolean resultCheck() {

        temp = getSudoku();

        return isEqual(finalPuzzle, temp);
    }

    /**
     * Final version of was created by
     * @author Suleman Kadiri
     * @author T00204198
     * @author Computing With Sofware Development
     *code reference
     * {@link https://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_Sudoku.html}
     */
}
