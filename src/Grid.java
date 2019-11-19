import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import static java.awt.Transparency.TRANSLUCENT;


/**Creating the grid game panel that also implements action listener that takes in the
 * the digits through a gui interface **/

public  class Grid extends JFrame {
	JPanel gPanel = new JPanel();
	JFormattedTextField textFields[][] = new JFormattedTextField[9][9];
	JPanel overallP = new JPanel();
	JPanel northPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	JButton btnCheck = new JButton("Check");




	public Grid() {
		super("Grid");
		setSize(400, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		gPanel.setLayout(new GridLayout(9, 9));
		gPanel.setSize(300, 300);
		btnCheck.addActionListener(new BtnCheck());
		final Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		JPanel pl = new GridPanel();


		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				/**Was trying to get each cell of the sudoko box restricted for only integer and a single value input
				 * had problems with the java document hence i used the JFormattedTextField
				 * https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
				 */
				textFields[i][j]  = null;
				try {
					textFields[i][j] = new JFormattedTextField(
							new MaskFormatter("#"));
					textFields[i][j].setColumns(1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				textFields[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
				textFields[i][j].addActionListener(new ActionListener() {
					//adding an action listener to the JText Fields.
					public void actionPerformed(ActionEvent e) {
						System.out.println("iT gOT TO THIS POINT 1");
						System.out.println("iT gOT TO THIS POINT");
						for (int i = 0; i < 9; i++) {
							for (int j = 0; j < 9; j++) {
								textFields[i][j].getText();
								System.out.println("key Typed" + textFields[i][j].getText());
							}
						}
					}
				});
				gPanel.add(textFields[i][j]);
				pl.add(textFields[i][j]);


			}
		}

		JLabel l = new JLabel("SUDOKO");
		l.setSize(100, 100);
		overallP.setLayout(new BorderLayout());
		northPanel.setLayout(new FlowLayout());
		northPanel.add(l);
		eastPanel.setLayout(new FlowLayout());
		eastPanel.add(btnCheck);
		overallP.add(northPanel, BorderLayout.NORTH);
		overallP.add(gPanel, BorderLayout.CENTER);
		overallP.add(eastPanel, BorderLayout.EAST);
		add(overallP);
		GridBagConstraints con = new GridBagConstraints();
		add(pl, con);
		con.gridx = 1;
		con.gridy = 0;
		con.gridwidth = 2;
		setVisible(true);

	}

	public class GridPanel extends JPanel {
		public void paint(Graphics g){
			super.paint(g);
			int w = getSize().width;
			int h = getSize().height;

			g.setColor(Color.black);
			g.drawRect(0,0,w-1,h-1);
			g.drawLine(w/2,0,w/2,h);
			g.drawLine(0,h/2,w,h/2);
		}
	}

	/**
	 * I had problems with the 2D array trying to get the reference instead of the contents
	 * I  did research on 2D arrays on http://math.hws.edu/javanotes/c7/s5.html and got to resolve
	 * the problem.
	 */

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
				digits[i][j] = (int) (Math.random() * ((9 - 1) + 1)) + 1;
				//digits[i][j] = 0;
				//digits[i][j] = Integer.parseInt(textFields[i][j].getText());
				textFields[i][j].setText(String.valueOf(digits[i][j]));
				/*if(Integer.parseInt(textFields[i][j].getText()) == 0){
					textFields[i][j].setText(String.valueOf(""));
				}*/

			}
					/*do{
						textFields[i][j].setText(String.valueOf(digits[i][j]));
					}while(textFields != null);*/
			//digits[i][j] = (int) (Math.random() * ((9 - 1) + 1)) + 1;


		}
	}
	// Remove the K no. of digits to
	// complete game
	public void removeMDigits()
	{
		int count = 65;
		while (count != 0)
		{
			int cellId = randomGenerator(9*9);

			// System.out.println(cellId);
			// extract coordinates i  and j
			int i = (cellId/9);
			int j = cellId%9;
			if (j != 0)
				j = j - 1;

			// System.out.println(i+" "+j);
			if (Integer.parseInt(textFields[i][j].getText())!= 0)
			{
				count--;
				textFields[i][j].setText(String.valueOf(0));
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
			removeMDigits();
		System.out.println("Welcome");
		} // end actionPerformed
	} // end CancelButtonHandler inner class


}