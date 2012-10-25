package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import mastermind_core.core;

public class gui {
	JButton[] code;
	JButton[] usingColors;
	JButton[] setIt;
	String[] setColors;
	int setColorPos;
	core mastermindCore;	
	
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

	private void initGame(int codeSize, Container container) {
		mastermindCore = new core(codeSize);
		usingColors = new JButton[codeSize];
		code = new JButton[codeSize / 2];
		setColors = new String[codeSize / 2];
		setColorPos = 0;
		
		//Generate secret code buttons
		for(int i=0; i < code.length; i++) {
			code[i] = new JButton();
			addComponent(container, code[i], i+i,0,2,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		}	
		
		//Buttons which get the choosed Colors 
		int tries = 4;
		int codeLength = codeSize / 2;
		setIt = new JButton[codeLength * tries]; //4 counts of trying
		int column = 1;
		int row = codeLength - 1;
		for(int i=(codeLength * tries)-1; i >= 0; i--) {
			setIt[i] = new JButton("" + i);
			addComponent(container, setIt[i], row,column,1,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
			row--;
			if(row==-1) {
				column++;
				row = codeLength - 1;
			}
		}	

		//Generate usedColor buttons
		String[] usedColors =	mastermindCore.getUsedColors();
		for(int i=0; i < usingColors.length; i++) {
			JButton chooseColors = new JButton ();
			chooseColors.setBackground(Color.decode(usedColors[i]));
			chooseColors.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						if(setColorPos<setIt.length) {
							JButton getColor = (JButton) e.getSource();
							setColors[setColorPos%code.length] = "#" + (Integer.toHexString(getColor.getBackground().getRGB())).substring(2); 
							setIt[setColorPos].setBackground(getColor.getBackground());
							if((setColorPos+1)%code.length == 0) {
								System.out.println("Versuch "+setColorPos/code.length+":");
								for(int i=0; i < setColors.length; i++) {
									System.out.print(setColors[i] + " - ");
								}
								System.out.println();
								String[] getHint = mastermindCore.color_check(setColors);
								for(int i=0; i < getHint.length; i++) {
									System.out.print(getHint[i] + " ");
								}	
								System.out.println();
							}
						} else {
							System.out.println("!!!!!!!!!!!!!GAME OVER :)!!!!!!!!!!!!!!!!!!!!");
						}
						setColorPos++;		
					}
			});
			setBackgroundColor();
			usingColors[i] = chooseColors;
			addComponent(container, usingColors[i], i,10,1,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
			
		}	
	}
	
	private void setBackgroundColor() {
		String [] colorCode = mastermindCore.generateCode();
		for(int i=0; i < code.length; i++) {
			code[i].setBackground(Color.decode(colorCode[i]));
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
						resetGame();
					}
			});
			menu.add(item);
			}
			bar.add(menu);
		}	
		((JFrame) container).setJMenuBar(bar);
	}
	
	private void resetGame() {
		for(int i=0; i<setIt.length; i++) {
			setIt[i].setBackground(null);
		}
		setColorPos=0;
		setBackgroundColor();
	}	
}
