package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind_core.core;
import mastermind_core.validator;
import mastermind_save_load.*;
import mastermind_gui.mastermind_templates.*;


class gameInitialization {
  private core mm_core;
  private gameData gD;
  private gameGround gG;

  public gameInitialization(core mm_core) {
    this.mm_core = mm_core;
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
    if (gD.setHint(mm_core.color_check())) {
      gD.setCellEditable(false);
      return gameStatus.WIN;
    } //Get pinSetting from Table, check it and put it back in table

    //Check tries
    if (mm_core.checkTries()) {
      gD.setCellEditable(false);
      gD.showCode();
      gG.setRowHeight(0,80);
      return gameStatus.LOST;
    }

    gD.addTry();
    return gameStatus.PLAYING;
  }

  public JPanel initenabledColors () {
    JPanel jp = new JPanel();
    String abc = "0QWER";
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

  protected void setColorAt(int color, int column) {
    if(color < mm_core.EnabledColorsSize() && column < gD.getColumnCount()-1 ) {
      gD.setColorAt(mm_core.getEnabledColors()[color],column);
    }
  }

  protected void removeColor(int column) {
    if(column < gD.getColumnCount() - 1) {
      gD.removeColor(column);
    }
  }

  public void setColor(String color) {
    gD.setColor(color);
  }

  public Object[] getCore() {
    return mm_core.makePKG();
  }

  protected void doitBot() {
    mm_core.doitBot();
  }

  protected validator validate() {
    return mm_core.validate();
  }

}
