package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind_core.core;
import mastermind_gui.mastermind_templates.*;


class gameInitialization {
  final int tries;
  final int codeLength;
  final int enabledColorRange;
  core mastermindCore;
  gameData gD;

  public gameInitialization(int codeLength,int enabledColorRange, int tries) {
    this.tries = tries;
    this.codeLength = codeLength;
    this.enabledColorRange = enabledColorRange;
    mastermindCore = new core(codeLength,enabledColorRange);
    String code[] = mastermindCore.generateCode();
    gD = new gameData(codeLength);
    gD.setArrayAt(code,0);
  }

  public gameGround initgameGround ()  {
    return new gameGround(gD,mastermindCore.getEnabledColors()); //Table with set and hint buttons
  }

  public void addTry() {
    gD.setHint(mastermindCore.color_check(gD.getpinSetting())); //Get pinSetting from Table, check it and put it back in table
    gD.addTry();
  }

  public JPanel initenabledColors () {
    JPanel jp = new JPanel();
    String abc = "QWERTZUIOP";
    String[] enabledColorsHEX = mastermindCore.getEnabledColors();
    for (int i=0; i < enabledColorRange; i++) {
      JButton jb;
      if ((i+1)  < 10) {
        jb = new JButton((i+1)+"");
      } else {
        jb = new JButton(abc.charAt(i-9)+"");
      }
      jb.setBackground(Color.decode(enabledColorsHEX[i]));
      jb.addActionListener(new ActionListener () {
        public void actionPerformed (ActionEvent e) {
          setColor("#" + (Integer.toHexString(((JButton)e.getSource()).getBackground().getRGB())).substring(2));
        }
      });
      jp.add(jb);
    }
    return jp;
  }

  public void setColor(String color) {
    gD.setColor(color);
  }
}
