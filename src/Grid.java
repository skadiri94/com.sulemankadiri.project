import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

/**Creating the grid game panel that also implements action listener that takes in the
 * the digits through a gui interface **/

public  class Grid extends JFrame implements ActionListener {
	JPanel p =new JPanel();
	JTextField textFields[][] = new JTextField[9][9];
	JPanel overallP =new JPanel();
	JPanel northPanel = new JPanel();
	JButton btnCheck = new JButton("Check");

	
	public Grid(){
		super("Grid");
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setLayout(new GridLayout(9,9));
		p.setSize(300,300);
		int digits=0;

		for(int i=0;i<9;i++){
            for (int j = 0; j < 9; j++) {
                textFields[i][j] = new JTextField();
                textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                textFields[i][j].setDocument(new JTextFieldLimit(1));
				textFields[i][j].addActionListener(new ActionListener() {
//adding an action listener to the JText Fields.
					public void actionPerformed(ActionEvent e) {
						System.out.println("iT gOT TO THIS POINT 1");
						System.out.println("iT gOT TO THIS POINT");
						for(int i=0;i<9;i++) {
							for (int j = 0; j < 9; j++) {
								textFields[i][j].getText();
								System.out.println("key Typed" + textFields[i][j].getText());
							}
						}
					}
				});
                p.add(textFields[i][j]);



			}
		}
		fillGrid(textFields);
		JLabel l = new JLabel("SUDOKO");
		l.setSize(100,100);
		overallP.setLayout(new BorderLayout());
		northPanel.setLayout(new FlowLayout());
		northPanel.add(l);
		overallP.add(northPanel,BorderLayout.NORTH);
		overallP.add(p,BorderLayout.CENTER);
        add(overallP);
		setVisible(true);

	}

	public int[][] getDigits(){
		//btnCheck.addActionListener(e ->
		int[] [] allNums = new int[9][9];

			for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
				    allNums[i][j] = Integer.parseInt(textFields[i][j].getText());
			//}
			return allNums;
			//});
		}

		public void fillGrid(JTextField[][] textFields){
			int[][] digits = new int[9][9];

			for(int i=0;i<digits.length;i++){
				for (int j = 0; j < digits.length; j++) {
					while(!textFields[i][j].getText().equals("")) {
						digits[i][j] = Integer.parseInt(textFields[i][j].getText());
						textFields[i][j].setText(String.valueOf(digits[i][j]));

					}
					/*do{
						textFields[i][j].setText(String.valueOf(digits[i][j]));
					}while(textFields != null);*/
					//digits[i][j] = (int) (Math.random() * ((9 - 1) + 1)) + 1;


				}
			}
		}
	public void actionPerformed(ActionEvent e) {

	}

}