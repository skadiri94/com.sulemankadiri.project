import javax.swing.*;
import java.awt.*;

/**First panel for the Sudoku grid and Game menu**/
public class SudokuFrame0 extends JFrame {

    //In Game menu for interact with the game like pause, play, stop and Save

    JMenu gameMenu,playerMenu,exitMenu;
    gPanel gp;

    /** Driver for the panel Creation**/

    public static void main(String[] args) {
        SudokuFrame0 sPanel = new SudokuFrame0();
        sPanel.setVisible(true);

    }

    /** a no argument constructor that creates the menu and populate them **/

    public SudokuFrame0() {

        //Setting the panel's default properties
        super("Sudoku");

        setSize(400,400);
        //setLocation(500,200);

        Container pane = getContentPane();

        pane.setPreferredSize(new Dimension(360, 360));
        JTextArea jta = new JTextArea();
        jta.setEditable(true);


        //registering an exit close button.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        gp = new gPanel();

        pane.add(gp, BorderLayout.CENTER);

        //creates the GameMenu
        createGameMenu();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //add game menu to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(playerMenu);
        menuBar.add(exitMenu);

        //pane.add(menuBar);



    }

    /**Creating and populating the file menu**/
    private void createGameMenu(){
        //Create the menu

        gameMenu = new JMenu("Menu");

        playerMenu= new JMenu("Player");

        exitMenu = new JMenu("Exit");



        //declaring a Menu Item (re-usable)
        JMenuItem item = new JMenuItem("New Game");

        gameMenu.add(item);

         item = new JMenuItem("Load Game");

        gameMenu.add(item);


        item = new JMenuItem("Save Game");

        gameMenu.add(item);

        item = new JMenuItem("Record");

        playerMenu.add(item);

        item = new JMenuItem("New Profile");

        playerMenu.add(item);

        item = new JMenuItem("Delete Delete");

        playerMenu.add(item);

    }


}
