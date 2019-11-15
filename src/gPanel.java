import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

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
    private final Color OPEN_CELL_TEXT_YES = new Color(100, 180, 100);
    private Color OPEN_CELL_TEXT_NO = new Color(200, 0, 50);
    private final Font FONT_SIZE = new Font("Arial", Font.BOLD, 16);
    private Sudoku puzzle = new Sudoku();
    private JFormattedTextField inputFields[][] = new JFormattedTextField[9][9];

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
        puzzle.fillSudoku();
        //puzzle.removeMNum(40);
        //System.out.print("Hello Man");
        //System.out.print(puzzle.toString());
        int [][]temp = new int[9][9];
        temp = puzzle.getSudoku();
        /** Allocating a listener to all the input Fields**/

        //InputListener listener = new InputListener();
        //Constructing the inputCells 9*9

        for (int row = 0; row < inputFields.length; ++row) {
            for (int col = 0; col < inputFields[row].length; ++col) {

                /**Was trying to get each cell of the sudoko box restricted for only integer and a single value input
                 * had problems with the java document hence i used the JFormattedTextField
                 * https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
                 */
                inputFields[row][col] = null;
                try {
                    inputFields[row][col] = new JFormattedTextField(
                            new MaskFormatter("#"));
                    inputFields[row][col].setColumns(1);//Sets the number of inputs allowed in a cell
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                inputFields[row][col].setHorizontalAlignment(JFormattedTextField.CENTER);//Aligns the input to the center
                inputFields[row][col].setText(temp[row][col] + " ");
                inputFields[row][col].setEditable(false);
                inputFields[row][col].setFont(FONT_SIZE);

                if (Integer.parseInt(inputFields[row][col].getText()) == 0) {
                    inputFields[row][col].setEditable(true);
                    inputFields[row][col].setText("");
                }

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