import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

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
    //In Game menu for interact with the game like level select, play, stop and Save
    JMenu gameMenu, playerMenu;
    JLabel lsudoku, lSelect, pNLabel;
    JPanel topPanel, rightPanel, index, finalPanel;
    JButton btnSubmit, btnPlay, levelB, levelI, levelM, createP, loadP;
    JTextField pName;
    GridPanel gp;

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

        loadProgress(fileStorage);
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
        item.addActionListener(this);
        gameMenu.add(item);

        item = new JMenuItem("Load Game");
        item.addActionListener(this);
        gameMenu.add(item);

        item = new JMenuItem("Save Game");
        item.addActionListener(this);
        gameMenu.add(item);

        gameMenu.addSeparator();
        item = new JMenuItem("Exit");
        item.addActionListener(this);
        gameMenu.add(item);

        item = new JMenuItem("Record");
        item.addActionListener(this);
        playerMenu.add(item);

        item = new JMenuItem("New Profile");
        item.addActionListener(this);
        playerMenu.add(item);

        item = new JMenuItem("Delete Profile");
        item.addActionListener(this);

        playerMenu.add(item);

    }

    public void actionPerformed(ActionEvent event) {
        String menuName;
        menuName = event.getActionCommand(); // what's written on the item that was clicked
        if (menuName.equals("Exit")) {
            saveProgress(fileStorage);
            System.exit(0);
        } // end if
        else if (menuName.equals("Save Game")) {

            GridPanel.showMessage("Game Saved");
        } //end else if

        else if (menuName.equals("Record")) {
            String txt;
            if (cplayer == null) {
                txt = "No Player Record Found! Load or Create Profile";
            } else
                txt = cplayer.toString();

            GridPanel.showMessage(txt);
        } // end if
        else if (menuName.equals("Load Game")) {
            goToIndex();
            createP.setVisible(false);
            cplayer = null;
            level = 0;
            pName.requestFocus();
        } else if (menuName.equals("New Game")) {
            goToUserIndex();
        } else if (menuName.equals("New Profile")) {
            goToIndex();
            cplayer = null;
            level = 0;
            pName.requestFocus();
        } else if (event.getSource() == btnPlay) {
            goToGame();

        } else if (menuName.equals("Delete Profile")) {
            String playerDel;
            cplayer = null;

            playerDel = JOptionPane.showInputDialog("Enter the Profile You wish to Delete");

            delectProfile(playerDel, player);
            saveProgress(fileStorage);

        } // end else

    }

    public void goToIndex() {
        createIndex();
        this.setContentPane(index);
        this.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
        this.revalidate();

    }

    public void goToUserIndex() {
        createIndex();
        level = 0;
        pName.setText(cplayer.getName());
        pName.setEditable(false);
        createP.setVisible(false);
        loadP.setVisible(false);
        this.setContentPane(index);
        this.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
        this.revalidate();

    }

    public void goToGame() {
        if (cplayer != null) {
            if (level != 0) {
                createGamePanel(level);
                this.setContentPane(finalPanel);
                this.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
                this.revalidate();
            } else
                JOptionPane.showMessageDialog(null, "Please Select level of Difficulty");
        } else
            JOptionPane.showMessageDialog(null, "Create Or Load a Profile");

    }

    public void saveProgress(File file) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            (objectOutputStream).writeObject(player);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void loadProgress(File file) {
        try (final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            player = (ArrayList<Player>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void createGamePanel(int level) {
        finalPanel = new JPanel();
        finalPanel.setSize(this.getSize());
        finalPanel.setLayout(new BorderLayout());
        JLabel lTimer = new JLabel("Time:");
        myTimer timer = new myTimer(100);

        gp = new GridPanel(level);

        topPanel = new JPanel();
        rightPanel = new JPanel();

        topPanel.setLayout(new FlowLayout());
        lsudoku = new JLabel("SUDOKU");
        topPanel.add(lsudoku);

        btnSubmit = new JButton("SUBMIT");
        inputFields = new JFormattedTextField[9][9];
        btnSubmit.addActionListener(e -> {
            String txt;
            if (!gp.resultCheck()) {
                txt = "Puzzle Not Complete or Incorrect Try again!";
                GridPanel.showMessage(txt);
            } else {
                txt = "Puzzle Complete!";
                timer.stop();
                cplayer.addScore(levelToString() + "  " + timer.getDisplayTime());
                GridPanel.showMessage(txt);
                saveProgress(fileStorage);
                goToGame();

            }

        });

        btnSubmit.setSize(40, 20);
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setPreferredSize(new Dimension(100, 100));
        lTimer.setBorder(new EmptyBorder(5, 0, 5, 0));
        rightPanel.add(lTimer);
        rightPanel.add(timer);
        rightPanel.add(btnSubmit, new FlowLayout());
        finalPanel.add(topPanel, BorderLayout.NORTH);
        finalPanel.add(rightPanel, BorderLayout.EAST);
        finalPanel.add(gp, BorderLayout.CENTER);

    }

    private void createIndex() {

        // creating a new GroupLayout object and associate it with the panel:
        index = new JPanel();
        JPanel northP, centerP, southP;
        JTextArea dText = new JTextArea();
        dText.setSize(50, 50);
        dText.setEditable(false);
        dText.setFont(new Font("SanSerif", Font.BOLD, 12));
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
            if (!nameP.equals("")) {
                if (!isFound(nameP, player)) {
                    cplayer = new Player(nameP);
                    player.add(cplayer);
                    txt = "New Profile '" +
                            cplayer.getName() + "' Created!\n\nSelect Level of Difficulty";
                    saveProgress(fileStorage);
                } else
                    txt = "Player Name already Exit";
            } else
                txt = "Field Must be Entered";

            dText.setText(txt);

        });
        loadP.addActionListener(e -> {
            String txt = "";
            String nameP = pName.getText();

            for (Player ply : player) {
                if (ply.getName().equals(nameP)) {
                    cplayer = ply;
                    txt = "Welcome Back " + cplayer.toString();
                    break;

                } else {
                    txt = "Player Not Found!";

                }
            }
            dText.setText(txt);


        });
        levelB.addActionListener(e -> level = 10);
        levelI.addActionListener(e -> level = 30);
        levelM.addActionListener(e -> level = 65);
        btnPlay.addActionListener(this);


        index.add(northP, BorderLayout.NORTH);
        index.add(centerP, BorderLayout.CENTER);
        index.add(southP, BorderLayout.SOUTH);
    }


    public void delectProfile(String plyr, ArrayList<Player> player) {

        Iterator<Player> plyItr = player.iterator();

        while (plyItr.hasNext()) {
            String demo = plyItr.next().getName();
            if (demo.equals(plyr)) {
                plyItr.remove();
                JOptionPane.showMessageDialog(null, "Deleted Successfully");
            }

        }

    }

    public boolean isFound(String plyr, ArrayList<Player> player) {

        for (Player pl : player) {
            if (pl.getName().equals(plyr))
                return true;

        }
        return false;
    }

    private String levelToString() {
        String txt = "";
        switch (level) {
            case 10:
                txt = "Beginners";
                break;
            case 30:
                txt = "Intermediate";
                break;
            case 65:
                txt = "Master";
                break;
        }
        return txt;
    }
}



