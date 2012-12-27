package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind_core.core;
import mastermind_core.validator;
import mastermind_save_load.*;
import mastermind_gui.mastermind_templates.*;

/**
 * Generate Game - connector between core/gameData and the world out there (gui)
 */
class gameInitialization {
  private core mm_core;
  private gameData gD;
  private gameGround gG;

  /**
   * Class construction - generating gameGround and gameData with core data
   *
   * @param mm_core core class
   * @see mastermind_core.core#data
   */
  public gameInitialization(core mm_core) {
    this.mm_core = mm_core;
    gD = new gameData(mm_core);
    gG = new gameGround(gD,mm_core.getEnabledColors()); //Table with set and hint buttons
  }

  /**
   * Need for current game status (boolean is to insufficient)
   */
  public enum gameStatus {
    WIN,PLAYING,LOST
  }

  /**
   * Get JTable generate by constructor
   *
   * @return JTable with core data
   */
  public gameGround initGameGround ()  {
    return gG;
  }

  /**
   * Add new try (next turn)
   *
   * @return current game status (Playing,Win,Lost)
   */
  public gameStatus addTry() {
    if (gD.setHint(mm_core.checkColor())) {
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

  /**
   * Build buttons from enabled color array
   *
   * @return color buttons placed on jpanel
   * @see mastermind_core.core#generateColors(int)
   */
  public JPanel initEnabledColors () {
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

  /**
   * Set value (HEX Color) at first row and given column in <code>data</code>
   *
   * @param color enabled color array index
   * @param column position to set value less than code size
   * @see mastermind_core.core#generateColors(int)
   * @see gameData#setColorAt(String,int)
   */
  protected void setColorAt(int color, int column) {
    if(color < mm_core.getEnabledColorsSize() && column < gD.getColumnCount()-1 ) {
      gD.setColorAt(mm_core.getEnabledColors()[color],column);
    }
  }

  /**
   * Remove value (HEX Color) at first row and given column in <code>data</code>
   *
   * @param column position to delete color from
   * @see gameData#removeColor(int)
   */
  protected void removeColor(int column) {
    if(column < gD.getColumnCount() - 1) {
      gD.removeColor(column);
    }
  }

  /**
   * Set color to next free (empty) position
   *
   * @param color HEX Color
   * @see gameData#setColor(String)
   */
  public void setColor(String color) {
    gD.setColor(color);
  }

  /**
   * Get important core objects (for saving purpose)
   *
   * @return core objects
   * @see mastermind_core.core#makePKG()
   */
  public Object[] getCore() {
    return mm_core.makePKG();
  }

  /**
   * Let the Bot check the turn (before creating a new try)
   */
  protected void doitBot() {
    mm_core.doitBot();
  }

  /**
   * Validate the turn
   *
   * @return <code>true</code> plus help text - go on playing (next try)<br>
   *         <code>false</code> plus help text - stop current turn - correct your fault
   * @see mastermind_core.validator
   */
  protected validator validate() {
    return mm_core.validate();
  }

}
