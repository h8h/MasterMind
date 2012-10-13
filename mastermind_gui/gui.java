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

		//Spielbare Farben und Namen ;)

		int codeSize = 8;
		mastermindCore = new core(codeSize);
		usingColors = new JButton[codeSize];
		code = new JButton[codeSize / 2];

		for(int i=0; i < code.length; i++) {
			code[i] = new JButton();
			addComponent(frame, code[i], i+i,0,2,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		}	
		setBackgroundColor();

		String[] usedColors =	mastermindCore.getUsedColors();
		for(int i=0; i < usingColors.length; i++) {
			JButton chooseColors = new JButton ();
			chooseColors.setBackground(Color.decode(usedColors[i]));
			usingColors[i] = chooseColors;
			addComponent(frame, usingColors[i], i,10,1,1, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		}

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


		frame.setJMenuBar(bar);
		//addComponent(frame, new JButton("BLA"), 15, 4, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
		/*/pane.setLayout(new GridBagLayout());
		Box hbox = Box.createHorizontalBox();
		Box hbox1 = Box.createHorizontalBox();
		

		



		//Action fuer den ReMixIt - Button
		JButton mixer = new JButton ("ReMixIt");

		//ReMixIt mit Click-Action verbinden
		mixer.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				setBackgroundColor();
			}
		});
		//ReMixIt-Button zum Panel hinzufuegen
		hbox1.add(mixer);
		Box vbox = Box.createVerticalBox();
		{
			//vbox.add(hbox);
			//vbox.add(pane);
			//vbox.add(hbox1);	    
		}

		//
		//frame.add(vbox);
		//Display the window.
		*/
		//frame.add(pane);
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
}
