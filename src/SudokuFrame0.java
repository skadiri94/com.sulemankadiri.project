import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * First panel for the Sudoku grid and Game menu
 **/
public class SudokuFrame0 extends JFrame implements ActionListener, Serializable {

    private File fileStorage;
    private ArrayList<Player> player = new ArrayList<>();
    private Player cplayer;
    private int level;
    private int[][] finalPuzzle = new int[9][9];
    private int[][] temp = new int[9][9];
    JFormattedTextField[][] inputFields = new JFormattedTextField[9][9];

    //In Game menu for interact with the game like pause, play, stop and Save
    JMenu gameMenu, playerMenu;
    JLabel lsudoku, lSelect, pNLabel;
    JPanel topPanel, rightPanel, index, finalPanel;
    JButton btnSubmit, btnPlay, levelB, levelI, levelM, createP, loadP;
    JTextField pName;
    gPanel gp;



    /**
     * a no argument constructor that creates the menu and populate them
     **/

    public SudokuFrame0() {

        //Setting the panel's default properties
        super("Sudoku");
        fileStorage = new File("Progress.dat");
        setSize(500, 500);
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

    /**
     * Driver for the panel Creation
     **/

    public static void main(String[] args) {
        SudokuFrame0 sPanel = new SudokuFrame0();
        sPanel.setVisible(true);

    }

    /**
     * Creating and populating the file menu
     **/
    private void createGameMenu() {
        //Create the menu

        gameMenu = new JMenu("Menu");

        playerMenu = new JMenu("Player");

        //Action Listener can't be added to an instance of JMune but with Jemune Item it can
        //exitMenu = new JMenu("Exit");

        //declaring a Menu Item (re-usable)
        JMenuItem item = new JMenuItem("New Game");

        gameMenu.add(item);

        item = new JMenuItem("Load Game");

        gameMenu.add(item);

        item = new JMenuItem("Save Game");
        item.addActionListener(this);

        gameMenu.add(item);

        item = new JMenuItem("Exit");
        item.addActionListener(this);

        gameMenu.add(item);

        item = new JMenuItem("Record");

        playerMenu.add(item);

        item = new JMenuItem("New Profile");

        playerMenu.add(item);

        item = new JMenuItem("Delete Delete");

        playerMenu.add(item);

    }


    public void actionPerformed(ActionEvent event) {
        String menuName;
        menuName = event.getActionCommand(); // what's written on the item that was clicked
        if (menuName.equals("Exit")) {
            System.exit(0);
        } // end if
        else {
            // timer.setText("Menu Item '" + menuName + "' is selected.");
        } // end else
        if (event.getSource() == btnPlay) {
            goToGame();

        }

    }

    public void goToGame() {
        if (level != 0) {
            createGamePanel(level);
            this.setContentPane(finalPanel);
            this.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
            this.revalidate();
        } else
            JOptionPane.showMessageDialog(null, "Please Select level of Difficulty");
    }

    public void saveProgress(File file) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            (objectOutputStream).writeObject(cplayer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("saved");

    }

    public void loadProgress(File file) {
        try (final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            cplayer = (Player) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("loaded");
    }

    private void createGamePanel(int level) {
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
        btnSubmit.addActionListener(e -> {
            gp.getSudoku();
            finalPuzzle = gp.getFinalPuzzle();
            temp = gp.getSudoku();
            String txt = "";
            if (!gp.resultCheck(finalPuzzle, temp).equals("Win!")) {
                txt = "Puzzle Not Complete or Incorrect Try again!";
                gPanel.showMessage(txt);
            } else {
                txt = "Puzzle Complete!";
                gPanel.showMessage(txt);
                createIndex();
                this.setContentPane(index);
                this.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
                this.revalidate();
            }

            System.out.print(gp.toString(temp));
            //gp.genPuzzle(level);

        });

        btnSubmit.setSize(40, 20);
        rightPanel.setLayout(new FlowLayout());
        rightPanel.add(btnSubmit, new FlowLayout());
        finalPanel.add(topPanel, BorderLayout.NORTH);
        finalPanel.add(rightPanel, BorderLayout.EAST);
        finalPanel.add(gp, BorderLayout.CENTER);

    }

    public void createIndex() {
        // creating a new GroupLayout object and associate it with the panel:
        index = new JPanel();
        JPanel northP, centerP, southP;
        JTextArea dText = new JTextArea();
        dText.setSize(50, 50);
        dText.setEditable(false);
        dText.setFont(new Font("SanSerif", Font.PLAIN, 14));
        dText.setForeground(Color.RED);

        index.setLayout(new BorderLayout());
        northP = new JPanel(new FlowLayout());
        northP.setBorder(new EmptyBorder(35, 5, 55, 5));//adds margin to panel
        northP.setAlignmentX(FlowLayout.CENTER);

        pNLabel = new JLabel("Player Name:");
        pName = new JTextField(10);
        createP = new JButton("Create Profile");
        loadP = new JButton("Load Profile");
        levelB = new JButton("Beginner");
        levelI = new JButton("Intermediate");
        levelM = new JButton("Master");
        btnPlay = new JButton("Play");
        //levelB.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        northP.add(pNLabel);
        northP.add(pName);
        northP.add(createP);
        northP.add(loadP);
        northP.add(dText);
        dText.setText("");
        centerP = new JPanel();
        centerP.setLayout(new FlowLayout());
        lSelect = new JLabel("Select Difficulty Level:");
        centerP.add(lSelect);
        centerP.add(levelB);
        centerP.add(levelI);
        centerP.add(levelM);
        southP = new JPanel(new FlowLayout());
        southP.add(btnPlay);
        southP.setBorder(new EmptyBorder(5, 5, 35, 5));//adds margin to panel

        pName.addActionListener(this);
        createP.addActionListener(e -> {
            String txt, nameP;

            nameP = pName.getText();
            if(!nameP.equals("")) {
                cplayer = new Player(nameP);
                txt = "New Profile '" +
                        nameP + "' Created!\n\nSelect Level of Difficulty";
                        saveProgress(fileStorage);
            }
            else
                txt = "Field Must be Entered";

            dText.setText(txt);

        });
        loadP.addActionListener(e -> {
            String txt;
            String nameP = pName.getText();
            loadProgress(fileStorage);
        if(cplayer.getName().equals(nameP)) {
                    cplayer = cplayer;
                    System.out.print("Welcome "+cplayer.toString());
                }
                else{
                    txt = "Player Not Found!";
                    dText.setText(txt);
                }



        });
        levelB.addActionListener(e -> {
            level = 10;
        });
        levelI.addActionListener(e -> {
            level = 30;
        });
        levelM.addActionListener(e -> {
            level = 65;
        });
        btnPlay.addActionListener(this);


        index.add(northP, BorderLayout.NORTH);
        index.add(centerP, BorderLayout.CENTER);
        index.add(southP, BorderLayout.SOUTH);
    }


}



