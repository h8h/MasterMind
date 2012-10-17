package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import mastermind_core.core;

public class gui {
	JButton[] code;
	JButton[] usingColors;
	core mastermindCore;	

// private void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill)

  public void showGUI() {
		//Erstellt ein neues Fenster		
		JFrame frame = new JFrame("MasterMind PP-1");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());

		//Make new Game
		initGame(8, frame);
		//Make Menu
		genMenu(frame);

		frame.pack();
		frame.setVisible(true);
	}

  private void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, new Insets(0, 0, 0, 0), 0, 0);
    container.add(component, gbc);
  }

	private void setBackgroundColor() {
		String [] colorCode = mastermindCore.generateCode();
		for(int i=0; i < code.length; i++) {
			code[i].setBackground(Color.decode(colorCode[i]));
		}
	}
	
	private void initGame(int codeSize, Container container) {
		mastermindCore = new core(codeSize);
		usingColors = new JButton[codeSize];
		code = new JButton[codeSize / 2];

		//Generate secret code buttons
		for(int i=0; i < code.length; i++) {
			code[i] = new JButton();
			addComponent(container, code[i], i+i,0,2,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		}	
		setBackgroundColor();

		//Generate usedColor buttons
		String[] usedColors =	mastermindCore.getUsedColors();
		for(int i=0; i < usingColors.length; i++) {
			JButton chooseColors = new JButton ();
			chooseColors.setBackground(Color.decode(usedColors[i]));
			usingColors[i] = chooseColors;
			addComponent(container, usingColors[i], i,10,1,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		}	
	}
	
	private void genMenu(Container container) {
		JMenuBar bar = new JMenuBar();
		{
			JMenu menu = new JMenu("File");
			{
			JMenuItem item = new JMenuItem("Neues Spiel");
			item.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						setBackgroundColor();
					}
			});
			menu.add(item);
			}
			bar.add(menu);
		}	
		((JFrame) container).setJMenuBar(bar);
	}
	
}
