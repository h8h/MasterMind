package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind_core.core;
import mastermind_save_load.*;
import mastermind_gui.mastermind_templates.*;


class gameInitialization {
  private int triescount=0;
  private core mm_core;
  private gameData gD;
  private gameGround gG;

  public gameInitialization(core mm_core) {
    this.mm_core = mm_core;
    String code[] = mm_core.generateCode();
    gD = new gameData(mm_core);
    gG = new gameGround(gD,mm_core.getEnabledColors()); //Table with set and hint buttons
  }

  public enum gameStatus {
    WIN,PLAYING,LOST
  }

  public gameGround initgameGround ()  {
    return gG;
  }

  public gameStatus addTry() {
    triescount++;
    if (gD.setHint(mm_core.color_check(gD.getpinSetting()))) {
      gD.setCellEditable(false);
      return gameStatus.WIN;
    } //Get pinSetting from Table, check it and put it back in table

    //Check tries
    //Check if last pin are black, else go on playing
    if (triescount == mm_core.getTries()) {
      gD.showCode();
      gG.setRowHeight(0,80);
      return gameStatus.LOST;
    }

    gD.addTry();
    return gameStatus.PLAYING;
  }

  public JPanel initenabledColors () {
    JPanel jp = new JPanel();
    String abc = "QWERTZUIOP";
    String[] enabledColorsHEX = mm_core.getEnabledColors();
    for (int i=0; i < enabledColorsHEX.length; i++) {
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
