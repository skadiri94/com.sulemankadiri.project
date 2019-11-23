import java.io.Serializable;
import java.util.*;

/**
 * The Sudoko Generator
 **/

public class Sudoku implements Serializable {


    private int superGrid; //The main 9*9 Grid.
    private int subGrid; // The sub 3*3 Grid.
    private int[][] puzzle;


    public Sudoku() {

        setSuperGrid(9);
        setSubGrid(3);
        puzzle = new int[superGrid][superGrid];


    }

    // Sudoku Generator
    public void fillSudoku() {
        // Fill the diagonal of 3*3 subGrid
        populateDiagonal();


        // Fill remaining blocks
        populateSuperGrid(0, subGrid);

        // Remove Randomly Missing digits to make game
        //removeMNum(20);
    }

    public int getSuperGrid() {
        return superGrid;
    }

    public void setSuperGrid(int superGrid) {
        this.superGrid = superGrid;
    }

    public int getSubGrid() {
        return subGrid;
    }

    public void setSubGrid(int subGrid) {
        this.subGrid = subGrid;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    //The method generates a random number between 1-9.
    int genRandomNum(int superGrid) {
        return ((int) (Math.random() * (superGrid - 1 + 1))) + 1;
    }

    /**
     * Method to check the row for exiting number, takes in the array of  Puzzel digits and index
     * of the row and the random number being generated
     */

    boolean rowCheck(int i, int ranNum) {
        for (int j = 0; j < superGrid; j++)
            if (puzzle[i][j] == ranNum)
                return false;
        return true;
    }

    /**
     * Method to check the column for exiting number, takes in the array of  Puzzel digits and index
     * of the row and the random number being generated
     */

    boolean colCheck(int j, int ranNum) {

        for (int i = 0; i < superGrid; i++)

            if (puzzle[i][j] == ranNum)

                return false;
        return true;
    }

    /**
     * Method to check the 3*3 subGrid for exiting number, takes in the array of  Puzzel digits and index
     * of the row and Column and the random number being generated
     */
    boolean subGridCheck(int startOfRow, int startOfCol, int ranNum) {
        for (int i = 0; i < subGrid; i++)
            for (int j = 0; j < subGrid; j++)
                if (puzzle[startOfRow + i][startOfCol + j] == ranNum)
                    return false;

        return true;
    }

    /**
     * This methods fills the cell of the SuperGrid 9*9 using the rowCheck,colCheck and subGridCheck methos
     * to check the existence of the randomly generated number
     */

    boolean cellCheck(int i, int j, int ranNum) {
        return (rowCheck(i, ranNum) && colCheck(j, ranNum) &&
                subGridCheck(i - i % subGrid, j - j % subGrid, ranNum));
    }

    //Populating the SubGrid 3*3
    void populateSubGrid(int row, int col) {

        int ranNum;
        for (int i = 0; i < subGrid; i++) {
            for (int j = 0; j < subGrid; j++) {
                do {
                    ranNum = genRandomNum(superGrid);

                } while (!cellCheck(row, col, ranNum));


                puzzle[row + i][col + j] = ranNum;


            }
        }

    }

    //Populating the Super Grid 9*9
    boolean populateSuperGrid(int i, int j) {
        //  System.out.println(i+" "+j);
        if (j >= superGrid && i < superGrid - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= superGrid && j >= superGrid)
            return true;

        if (i < subGrid) {
            if (j < subGrid)
                j = subGrid;
        } else if (i < superGrid - subGrid) {
            if (j == (i / subGrid) * subGrid)
                j = j + subGrid;
        } else {
            if (j == superGrid - subGrid) {
                i = i + 1;
                j = 0;
                if (i >= superGrid)
                    return true;
            }
        }

        for (int num = 1; num <= superGrid; num++) {
            if (cellCheck(i, j, num)) {
                puzzle[i][j] = num;
                if (populateSuperGrid(i, j + 1))
                    return true;

                puzzle[i][j] = 0;
            }
        }
        return false;
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    void populateDiagonal() {

        for (int i = 0; i < superGrid; i = i + subGrid)

            // for diagonal box, start coordinates->i==j
            populateSubGrid(i, i);
    }

    public void removeMNum(int mNum) {
        int count = mNum;
        while (count != 0) {
            int cellIndex = genRandomNum(superGrid * superGrid);


            //System.out.println(cellIndex);
            // extract coordinates i  and j
            int i = (cellIndex / superGrid);
            int j = cellIndex % superGrid;

            if (j != 0)
                j = j - 1;

            // System.out.println(i+" "+j);
            if (puzzle[i][j] != 0) {
                count--;
                puzzle[i][j] = 0;
            }
        }
    }

    /**
     * I had problems with the 2D array trying to get the reference instead of the contents
     * I  did research on 2D arrays on http://math.hws.edu/javanotes/c7/s5.html and got to resolve
     * the problem.
     **/

    public int[][] getSudoku() {
        int[][] num = new int[9][9];

        for (int i = 0; i < this.puzzle.length; i++) {
            for (int j = 0; j < this.puzzle[i].length; j++) {
                num[i][j] = this.puzzle[i][j];
            }
        }

        return num;
    }


    public String toString() {
        String digits = "";
        for (int i = 0; i < superGrid; i++) {
            for (int j = 0; j < superGrid; j++) {
                digits += puzzle[i][j] + " ";

            }
            digits += "\n";
        }


        return digits;
    }
}



