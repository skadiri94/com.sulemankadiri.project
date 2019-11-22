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
    private int level;
    JMenu gameMenu,playerMenu;
    JLabel lsudoku,gTitle,pNLabel;
    JPanel topPanel,rightPanel,index,finalPanel;
    JButton btnSubmit,btnPlay,levelB,levelI,levelM;
    //JTextArea timer = new JTextArea("Time");
    JTextField pName;
    gPanel gp;
    JFormattedTextField[][] inputFields = new JFormattedTextField[9][9];
    private int [][] finalPuzzle = new int[9][9];
    private int [][] temp = new int[9][9];


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

        //registering an exit close button.
        setDefaultCloseOperation(EXIT_ON_CLOSE);

       //Creating the Index Panel for player registration and game level select

        createIndex();
        pane.add(index);







        //creates the GameMenu
        createGameMenu();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //add game menu to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(playerMenu);
        //menuBar.add(exitMenu);




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
        if (menuName.equals("Exit")) {
            System.exit(0);
        } // end if
        else {
           // timer.setText("Menu Item '" + menuName + "' is selected.");
        } // end else
        if(event.getSource() == btnPlay){
            if(level != 0) {
                createGamePanel(level);
                this.setContentPane(finalPanel);
                this.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
                this.revalidate();
            }
            else
                JOptionPane.showMessageDialog(null,"Please Select level of Difficulty" );
            }




        }


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
            Player[][] player = (Player[][]) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("loaded");
    }

    private void createGamePanel(int level){
        finalPanel = new JPanel();
        finalPanel.setSize(this.getSize());
        finalPanel.setLayout(new BorderLayout());
        JTextArea jta = new JTextArea();
        jta.setEditable(true);

        gp = new gPanel(level);

        topPanel = new JPanel();
        rightPanel = new JPanel();

        topPanel.setLayout(new FlowLayout());
        lsudoku = new JLabel("SUDOKU");
        topPanel.add(lsudoku);

        btnSubmit = new JButton("SUBMIT");
        inputFields = new JFormattedTextField[9][9];
        btnSubmit.addActionListener(e -> {gp.getSudoku(inputFields);
                finalPuzzle=gp.getFinalPuzzle();
                temp = gp.getSudoku(inputFields);
            gp.resultCheck(finalPuzzle,temp);
        });
        btnSubmit.setSize(40,20 );
        rightPanel.setLayout(new FlowLayout());
        //timer.setEditable(false);
        // String tm ="";

        //runTimer(tm);
        //timer.setText(tm);

        //rightPanel.add(timer, BorderLayout.NORTH);
        rightPanel.add(btnSubmit, BorderLayout.SOUTH);
        rightPanel.add(btnSubmit, new FlowLayout());

        finalPanel.add(topPanel, BorderLayout.NORTH);
        finalPanel.add(rightPanel, BorderLayout.EAST);

        finalPanel.add(gp, BorderLayout.CENTER);



    }

    public void createIndex(){
        // creating a new GroupLayout object and associate it with the panel:
        index = new JPanel();
        index.setLayout(new FlowLayout());

        pNLabel = new JLabel("Player Name:");
        pName = new JTextField(10);
        levelB = new JButton("Beginner");
        levelI = new JButton("Intermediate");
        levelM = new JButton("Master");
        btnPlay = new JButton("Play");
        levelB.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //defines the groups and add the components






        pName.addActionListener(this);
        levelB.addActionListener(e -> {level =10;});
        levelI.addActionListener(e -> {level =30;});
        levelM.addActionListener(e -> {level =65;});
        btnPlay.addActionListener(this);


        index.add(pNLabel);
        index.add(pName);
        index.add(levelB);
        index.add(levelI);
        index.add(levelM);
        index.add(btnPlay);
    }


    }



