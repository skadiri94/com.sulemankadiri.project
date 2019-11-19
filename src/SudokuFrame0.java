import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**First panel for the Sudoku grid and Game menu**/
public class SudokuFrame0 extends JFrame implements ActionListener, Serializable {

    //In Game menu for interact with the game like pause, play, stop and Save
    private  File fileStorage;
    private Player player;
    JMenu gameMenu,playerMenu;
    JLabel lsudoku;
    JPanel topPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JButton btnSubmit = new JButton("SUBMIT");
    JTextArea timer = new JTextArea("Time");
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
        fileStorage = new File("Progress.dat");
       // player = new Player();
        setSize(500,500);
        //setLocation(500,200);
        setResizable(false);

        Container pane = getContentPane();

        pane.setPreferredSize(new Dimension(360, 360));
        JTextArea jta = new JTextArea();
        jta.setEditable(true);


        //registering an exit close button.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        gp = new gPanel();

        topPanel.setLayout(new FlowLayout());
        lsudoku = new JLabel("SUDOKU");
        topPanel.add(lsudoku);

        btnSubmit.setSize(40,20 );
        rightPanel.setLayout(new BorderLayout());
        timer.setEditable(false);
        rightPanel.add(timer, BorderLayout.NORTH);
        rightPanel.add(btnSubmit, BorderLayout.SOUTH);

        pane.add(topPanel, BorderLayout.NORTH);
        pane.add(rightPanel, BorderLayout.EAST);

        pane.add(gp, BorderLayout.CENTER);



        //creates the GameMenu
        createGameMenu();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //add game menu to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(playerMenu);
        //menuBar.add(exitMenu);

        //pane.add(menuBar);



    }

    /**Creating and populating the file menu**/
    private void createGameMenu(){
        //Create the menu

        gameMenu = new JMenu("Menu");

        playerMenu= new JMenu("Player");

        //Action Listener can't be added to an instance of JMune but with Jemune Item it can
        //exitMenu = new JMenu("Exit");




        //declaring a Menu Item (re-usable)
        JMenuItem item = new JMenuItem("New Game");

        gameMenu.add(item);

         item = new JMenuItem("Load Game");

        gameMenu.add(item);


        item = new JMenuItem("Save Game");
        item.addActionListener( this );

        gameMenu.add(item);

        item = new JMenuItem("Exit");
        item.addActionListener( this );

        gameMenu.add(item);

        item = new JMenuItem("Record");

        playerMenu.add(item);

        item = new JMenuItem("New Profile");

        playerMenu.add(item);

        item = new JMenuItem("Delete Delete");

        playerMenu.add(item);

    }

    public void actionPerformed(ActionEvent event) {
        String  menuName;
        menuName = event.getActionCommand(); // what's written on the item that was clicked
        // note the String comparison
        if (menuName.equals("Exit")) {
            System.exit(0);
        } // end if
        else {
            timer.setText("Menu Item '" + menuName + "' is selected.");
        } // end else
    }
/*
    public void saveProgress(File file) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            (objectOutputStream).writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("saved");

    }

    public void loadProgress(File file) {
        try (final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            player = (Player[][]) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("loaded");
    }*/

    }



