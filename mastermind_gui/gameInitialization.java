package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import mastermind_core.core;

class gameInitialization {
	Container con;
	JButton[] code;
	JButton[] enabledColors;
	JButton[] usedColors;
	JButton[] hints;
	String[] setColorsHEX;
	int setColorPos;
	final int tries;
	core mastermindCore;
	

  public gameInitialization(int codeLength,int enabledColorRange, int tries) {
		//Erstellt ein neues Fenster		
		con = new JPanel();
		con.setLayout(new GridBagLayout());
		//Make new Game
		this.tries = tries;
		initGame(codeLength, enabledColorRange, tries);
	}

	private void initGame(int codeLength,int enabledColorRange, int tries) {
		mastermindCore = new core(codeLength, enabledColorRange);
		enabledColors = new JButton[enabledColorRange];

		code = new JButton[codeLength];
		setColorsHEX = new String[codeLength];
		setColorPos = 0;
		
		//Generate secret code buttons
		for(int i=0; i < code.length; i++) {
			code[i] = new JButton();
		}	

		hints = new JButton[codeLength*tries];
		usedColors = new JButton[codeLength * tries];
		int column = 1;
		int row = -1;
		JPanel hintPane = new JPanel();
		for(int i=(codeLength * tries)-1; i >= 0; i--) {
			if(row==-1) {
				column++;
				row = codeLength - 1;
				hintPane = new JPanel();
				hintPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				GridLayout hintLayout = new GridLayout(codeLength/2,codeLength/2);
				hintPane.setLayout(hintLayout);
				addComponent(hintPane, code.length+1,column,1,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);	
			}
			usedColors[i] = new JButton("" + i);
			addComponent(usedColors[i], row,column,1,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
			hints[i] = new JButton(i +"");
			hintPane.add(hints[i]);
			row--;
		}	
		
		//Generate usedColor buttons
		String[] enabledColorsHEX =	mastermindCore.getEnabledColors();
		for(int i=0; i < enabledColors.length; i++) {
			JButton chooseColors = new JButton ();
			chooseColors.setBackground(Color.decode(enabledColorsHEX[i]));
			chooseColors.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						if(setColorPos<usedColors.length) {
							JButton getColor = (JButton) e.getSource();
							setColorsHEX[setColorPos%code.length] = "#" + (Integer.toHexString(getColor.getBackground().getRGB())).substring(2); 
							usedColors[setColorPos].setBackground(getColor.getBackground());
							if((setColorPos+1)%code.length == 0) {
							String[] getHint = mastermindCore.color_check(setColorsHEX);
							int hintPos = setColorPos;
							for(int i=0; i < getHint.length; i++) {
									if(getHint[i]=="X") {
										hints[hintPos].setBackground(Color.BLACK);
										hintPos--;
									} else if(getHint[i]=="O") {
										hints[hintPos].setBackground(Color.WHITE);
										hintPos--;
									}
							}



								
								
							}
							if(setColorPos == usedColors.length-1) {
								System.out.println("!!!!!!!!!!!!!GAME OVER :)!!!!!!!!!!!!!!!!!!!!");
								showResult();
							}
						} else {	
							System.out.println("!!!!!!!!!!!!!GAME ALREADY OVER :)!!!!!!!!!!!!!!!!!!!!");
						}
						setColorPos++;		
					}
			});
			setBackgroundColor();
			enabledColors[i] = chooseColors;
			addComponent(enabledColors[i], i,10,1,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
			
		}	
	}
	
	private void showResult() {
		//Generate secret code buttons
		for(int i=0; i < code.length; i++) {
			addComponent(code[i], i+i,0,2,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		}	
	}
		
	private void setBackgroundColor() {
		String [] colorCode = mastermindCore.generateCode();
		for(int i=0; i < code.length; i++) {
			code[i].setBackground(Color.decode(colorCode[i]));
		}
	}
	
	private void resetGame() {
		for(int i=0; i<usedColors.length; i++) {
			usedColors[i].setBackground(null);
			hints[i].setBackground(null);		
		}
		setColorPos=0;
		setBackgroundColor();
	}	
	 
	protected Container getContainer() {
			return con;	
	}
	
	private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, new Insets(0, 0, 0, 0), 0, 0);
    con.add(component, gbc);
    con.revalidate();
  }
}
