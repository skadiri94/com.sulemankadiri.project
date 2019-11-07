import javax.swing.*;
import java.awt.*;

/**First panel for the Sudoku grid and Game menu**/
public class SudokuFrame0 extends JFrame {

    //In Game menu for interact with the game like pause, play, stop and Save

    JMenu gameMenu;

    /** Driver for the panel Creation**/

    public static void main(String[] args) {
        SudokuFrame0 sPanel = new SudokuFrame0();
        sPanel.setVisible(true);

    }

    /** a no argument constructor that creates the menu and populate them **/

    public SudokuFrame0() {

        //Setting the panel's default properties
        setTitle("Sudoku");
        setSize(900,600);
        setLocation(500,200);
        Container pane = getContentPane();
        pane.setBackground(new Color(200,200,240));
        JTextArea jta = new JTextArea();
        jta.setEditable(false);
        JPanel panel0 = new JPanel();
        panel0.setPreferredSize(new Dimension(300,400));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel0,new JScrollPane(jta));
        splitPane.setPreferredSize(new Dimension(300,300));
        splitPane.setBackground(new Color(150,100,240));
        pane.add(splitPane);

        //registering an exit close button.
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //creates the GameMenu
        createGameMenu();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //add game menu to the menu bar
        menuBar.add(gameMenu);



    }

    /**Creating and populating the file menu**/
    private void createGameMenu(){
        //Create the menu

        gameMenu = new JMenu("Menu");

        //declaring a Menu Item (re-usable)
        JMenuItem item = new JMenuItem("New Game");

        gameMenu.add(item);

    }


}
