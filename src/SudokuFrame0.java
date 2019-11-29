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

    //In Game menu for interact with the game like level select, play, stop and Save
    JMenu gameMenu, playerMenu;
    JLabel lsudoku, lSelect, pNLabel;
    JPanel topPanel, rightPanel, index, finalPanel;
    JButton btnSubmit, btnPlay, levelB, levelI, levelM, createP, loadP;
    JTextField pName;
    GridPanel gp;
    private File fileStorage;
    private ArrayList<Player> player = new ArrayList<>();
    private Player cplayer;
    private int level;

    public SudokuFrame0() {

        //Setting the panel's default properties
        super("Sudoku");
        fileStorage = new File("Progress.dat");
        setSize(500, 500);
        setLocation(600, 300);
        setResizable(false);
        Container pane = getContentPane();
        //registering an exit close button.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/icon.png"));
        setIconImage(icon.getImage());


        //check to see if file is found, if its found load the file
        if (fileStorage.exists()) {
            loadProgress(fileStorage);
        }

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
     * a no argument constructor that creates the menu and populate them
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
        switch (menuName) {
            case "Exit":
                saveProgress(fileStorage);
                System.exit(0);
            case "Save Game":
                if (cplayer != null)
                    saveProgress(fileStorage);

                GridPanel.showMessage("Game Saved");
                break;
            case "Record":
                String txt;
                if (cplayer == null) {
                    txt = "No Player Record Found! Load or Create Profile";
                } else
                    txt = cplayer.toString();

                GridPanel.showMessage(txt);
                break;
            case "Load Game":
                goToIndex();
                createP.setVisible(false);
                cplayer = null;
                level = 0;
                pName.requestFocus();
                break;
            case "New Game":
                goToUserIndex();
                break;
            case "New Profile":
                goToIndex();
                cplayer = null;
                level = 0;
                pName.requestFocus();
                break;
            case "Delete Profile":
                String playerDel;
                cplayer = null;

                playerDel = JOptionPane.showInputDialog("Enter the Profile You wish to Delete");
                delectProfile(playerDel, player);
                saveProgress(fileStorage);

                break;
        }

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
        if (cplayer != null) {
            pName.setText(cplayer.getName());
            pName.setEditable(false);
            createP.setVisible(false);
            loadP.setVisible(false);
            this.setContentPane(index);
            this.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
            this.revalidate();
        } else
            JOptionPane.showMessageDialog(null, "No Player Profile loaded");
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

        // btnSubmit = new JButton("SUBMIT");

        Icon icon = new ImageIcon(getClass().getResource("/resources/submit.png"));
        btnSubmit = new JButton(icon);
        btnSubmit.setPreferredSize(new Dimension(78, 25));
        btnSubmit.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnSubmit.setOpaque(false);
        btnSubmit.addActionListener(e -> {
            String txt;
            if (!gp.resultCheck()) {
                txt = "Puzzle Not Complete or Incorrect Try again!";
                GridPanel.showMessage(txt);
            } else {
                txt = "Puzzle Complete!";
                timer.stop();
                cplayer.addScore(levelToString() + "    " + timer.getDisplayTime());
                saveProgress(fileStorage);
                GridPanel.showMessage(txt);
                if (JOptionPane.showConfirmDialog(null, "Replay Level", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    goToGame();
                else
                    goToUserIndex();

            }

        });

        //btnSubmit.setSize(40, 20);

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


        index = new JPanel(new FlowLayout());
        JLabel bgImg = new JLabel(new ImageIcon(getClass().getResource("/resources/592.jpg")));
        JPanel northP, centerP, southP;
        JTextArea dText = new JTextArea(8, 20);

        JScrollPane scroll = new JScrollPane();
        JViewport viewport = new JViewport();

        //Component that need to be added in Scroll pane//

        viewport.setView(new JPanel());
        viewport.setOpaque(false);
        scroll.setViewport(viewport);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        //JScrollPane scrollPane = new JScrollPane();

        // JScrollPane scroll = new JScrollPane(dText);
        // scroll.setOpaque(false);
        scroll.setHorizontalScrollBar(null);
        scroll.setBorder(new EmptyBorder(0, 0, 0, 0));
        scroll.getViewport().add(dText, null);

        dText.setSize(50, 50);
        dText.setEditable(false);
        dText.setOpaque(false);
        dText.setFont(new Font("Serif Plain", Font.PLAIN, 15));
        dText.setForeground(Color.white);

        index.add(bgImg);
        bgImg.setLayout(new BorderLayout());
        northP = new JPanel(new FlowLayout());
        northP.setOpaque(false);
        northP.setBorder(new EmptyBorder(35, 5, 55, 5));//adds margin to panel
        northP.setAlignmentX(FlowLayout.CENTER);

        pNLabel = new JLabel("Player Name:");
        pNLabel.setFont(new Font("Serif Plain", Font.PLAIN, 16));
        pNLabel.setForeground(Color.white);
        pName = new JTextField(10);
        pName.setOpaque(false);
        pName.setForeground(Color.white);
        pName.setFont(new Font("Serif Plain", Font.PLAIN, 16));
        //setBackground(new Color(0,0,0,55));
        createP = new JButton("Create Profile");
        createP.setBackground(new Color(179, 0, 0));
        createP.setForeground(Color.white);
        createP.setBorder(new EmptyBorder(5, 5, 5, 5));
        loadP = new JButton("Load Profile");
        loadP.setBackground(new Color(0, 72, 186));
        loadP.setForeground(Color.white);
        loadP.setBorder(new EmptyBorder(5, 5, 5, 5));
        levelB = new JButton("Beginner");
        levelB.setBackground(new Color(187, 51, 255));
        levelB.setForeground(Color.white);
        levelB.setBorder(new EmptyBorder(5, 5, 5, 5));
        levelI = new JButton("Intermediate");
        levelI.setBackground(new Color(255, 140, 25));
        levelI.setForeground(Color.white);
        levelI.setBorder(new EmptyBorder(5, 5, 5, 5));
        levelM = new JButton("Master");
        levelM.setBackground(new Color(255, 25, 25));
        levelM.setForeground(Color.white);
        levelM.setBorder(new EmptyBorder(5, 10, 5, 10));
        Icon icon = new ImageIcon(getClass().getResource("/resources/ply12.png"));
        btnPlay = new JButton(icon);
        btnPlay.setPreferredSize(new Dimension(100, 58));
        //btnPlay.setForeground(new Color(255, 255, 255).brighter());
        btnPlay.setOpaque(false);
        btnPlay.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnPlay.setBackground(new Color(250, 255, 250, 60));

        northP.add(pNLabel);
        northP.add(pName);
        northP.add(createP);
        northP.add(loadP);
        northP.add(scroll);
        dText.setText("");
        centerP = new JPanel();
        centerP.setLayout(new FlowLayout());
        centerP.setPreferredSize(new Dimension(100, 100));
        centerP.setOpaque(false);
        lSelect = new JLabel("Select Difficulty Level:");
        lSelect.setFont(new Font("Serif Plain", Font.PLAIN, 16));
        lSelect.setForeground(Color.white);
        centerP.add(lSelect);
        centerP.add(levelB);
        centerP.add(levelI);
        centerP.add(levelM);
        southP = new JPanel(new FlowLayout());
        southP.add(btnPlay);
        southP.setOpaque(false);
        southP.setBorder(new EmptyBorder(5, 5, 35, 5));//adds margin to panel
        southP.setPreferredSize(new Dimension(100, 150));

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
            if (!pName.getText().equals("")) {
                if (!player.isEmpty()) {
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
                } else
                    txt = "No file has being Saved Or Loaded";
            } else
                txt = "Enter Name!";
            dText.setText(txt);

        });
        levelB.addActionListener(e -> level = 10);
        levelI.addActionListener(e -> level = 30);
        levelM.addActionListener(e -> level = 65);
        btnPlay.addActionListener(e -> goToGame());

        bgImg.add(northP, BorderLayout.NORTH);
        bgImg.add(centerP, BorderLayout.CENTER);
        bgImg.add(southP, BorderLayout.SOUTH);

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
                txt = "Beginners   ";
                break;
            case 30:
                txt = "Intermediate";
                break;
            case 65:
                txt = "Master        ";
                break;
        }
        return txt;
    }


}



