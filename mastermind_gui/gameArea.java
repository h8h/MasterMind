package mastermind_gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.*;

/**
 * JFrame with extra features<br>
 * Main frame to get MasterMind PP-1 running
 */
@SuppressWarnings("serial")
class gameArea extends JFrame implements WindowListener{
  protected JButton nextTry;
  private JLabel helpText;
  protected gameOptions options;
  private JPanel enabledColors;
  private Box gameInit;
  private gui game;
  private gameDialog gD;

  /**
   * Class construction
   *
   * @param game back reference on game gui, for example to add try
   * @see gui
   */
  public gameArea(gui game) {
    this.game = game;

    initFrame();
    // KeyListener for add / delete colors on gameGround
    setKeyListener(new gameKeyboard(game));

    options = new gameOptions(game);

    add(options,BorderLayout.LINE_START);

    nextTry = new JButton ("OK");
    nextTry.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent e) {
        addTry();
      }
    });
    add(nextTry,BorderLayout.LINE_END);

    helpText = new JLabel("Errate den geheimen Code ... | ");
    helpText.setBorder(BorderFactory.createTitledBorder(""));
    helpText.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(helpText,BorderLayout.NORTH);

    pack();
    setVisible(true);
  }

  /**
   * Set design and size
   */
  private void initFrame() {
    setPreferredSize(new Dimension(800, 550));

    addComponentListener(new java.awt.event.ComponentAdapter() {
        public void componentResized(ComponentEvent event) { //Set minimum window size
          setSize(
            Math.max(100, getWidth()),
            Math.max(100, getHeight()));
          }
      });
    addWindowListener(this);

    setMinimumSize(getPreferredSize());
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
  }

  /**
   * Select which game dialog to open
   */
  public enum gameDialogSelector {
	    HELP,INFO
  }
  /**
   * Exit game and dispose window, if no game is running
   *
   * @see gui#gameRunning()
   */
  private void exitGame() {
    if(!game.gameRunning())
        dispose();
  }

  /**
   * Add new try
   *
   * @see gui#addTry()
   */
  private void addTry() {
    game.addTry();
  }

  /**
   * Set text which is place on left upper corner of the frame
   *
   * @param s text you want to set (overwrites the old text)
   */
  protected void setText(String s) {
    helpText.setText(s);
  }

  /**
   * Get text which is place on left upper corner of the frame
   *
   * @return help text
   */
  protected String getText(){
    return helpText.getText();
  }

  /**
   * Add enabled color panel from given panel
   *
   * @param eC JPanel on which the enabled colors are place
   * @see gameInitialization#initEnabledColors()
   */
  protected void setEnabledColors(JPanel eC) {
    removeEnabledColors();
    enabledColors = eC;
    enabledColors.setBorder(BorderFactory.createTitledBorder(""));
    add(enabledColors,BorderLayout.SOUTH);
    repaint();
    revalidate();
  }

  /**
   * Return enabled color panel
   *
   * @return JPanel on which the enabled colors are place
   * @see #setEnabledColors(JPanel)
   */
  protected JPanel getEnabledColors() {
    return enabledColors;
  }

  /**
   * Remove enabled colors panel from frame
   */
  protected void removeEnabledColors() {
    if(enabledColors == null) { return; }
    remove(enabledColors);
    repaint();
    revalidate();
  }

  /**
   * Add createGame from given box
   *
   * @param gI Box on which the 'createGame' is place
   * @see gameInitialization#createGame()
   */
  protected void setGameInitialization(Box gI) {
    removeGameInitialization();
    gameInit = gI;
    gameInit.setBorder(BorderFactory.createTitledBorder(" "));
    add(gI,BorderLayout.CENTER);
    repaint();
    revalidate();
  }

  /**
   * Remove table (gameGround)
   * @see gameGround
   */
  protected void removeGameInitialization() {
    if(gameInit == null) { return; }
    remove(gameInit);
    repaint();
    revalidate();
  }

  /**
   * En/Disable menu entries
   *
   * @param b <code>true</code> enable menu entry<br>
   *          <code>false</code> disable menu entry
   */
  protected void setEnabledSaveMenu(boolean b) {
   getJMenuBar().getMenu(0).getItem(2).setEnabled(b); // item01
   getJMenuBar().getMenu(0).getItem(3).setEnabled(b); // item02
  }

  /**
   * En/Disable next try button
   *
   * @param b <code>true</code> enable button<br>
   *          <code>false</code> disable Button
   */
  protected void setEnabledNextTry(boolean b) {
    nextTry.setEnabled(b);
  }

  /**
   * Set manualCode, by requesting on options checkbox
   *
   * @see gameOptions#manualCode
   * @see gameOptions#initManualCode
   */
  protected void initManualCode() {
    options.initManualCode();
  }

  /**
   * Check how to set code - automatically or manually
   *
   * @return <code>true</code> if code should be set manually<br>
   *         <code>false</code> if code should be set auto
   * @see gameOptions#manualCode
   */
  protected boolean isManualCode() {
    return options.manualCode;
  }

  /**
   * Set manualCode in options gui to false, ready to start game
   * @see gameOptions#manualCode
   */
  protected void removeManualCode() {
    options.manualCode = false;
  }

  /**
   * Set and activate key listener from given keyboard class
   *
   * @param k keyboard class
   * @see gameKeyboard
   */
  protected void setKeyListener(gameKeyboard k) {
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(k);
  }

  /**
   * Show Info / About Dialog
   */
  protected void showDialog(gameDialogSelector gDS) {
	  switch(gDS) {
      case HELP:
          gD = new gameDialog("Anleitung","<html><br><font size =+2>Anleitung</font><br><br>Klicke auf die Farben, um sie in ein leeres Feld einzusetzen oder klicke auf die Felder um die Farbe zu wechseln.<br><br>Alternativ, klicke die Taste <b>a</b>, dann die Farbnummer (1-9/0 für 10 bzw. Q W E R), dann die Spalte(1 - 9 / 0 für 10).<br><br>Zum löschen klicke die Taste <b>d</b>, dann die Spaltennummer.<br><br>Alt+p startet ein neues Spiel.<br><br>Leere Felder (grau) füllt der Computer, nach klicken der OK oder n-Taste, automatisch aus.<br><br>Durch benutzen der Hilfefunktion, können unnötige Spielzüge verhindert werden, indem der Computer einen entsprechenden Hinweis gibt.<br><br> Ein <b>schwarzer Pin</b> bedeutet richtige Farbe und richtiger Platz.<br><br>Ein <b>roter Pin</b>, meist auch bekannt mit weißem Pin, bedeutet richtige Farbe und falscher Platz.</html>");
        break;
      case INFO:
    	  gD = new gameDialog("Programminfo","<html><br><font size=+2>Info</font><br><br>MasterMind PP-1 wurde im Rahmen eines Hochschulprojektes entwickelt, in der Hoffnung es ist intuitiv und hilfreich, um die grauen Zellen zu reaktivieren.<br><br><b><u>Wichtig: </u>Für evtl. geschädigte oder zerstörte Gehirnzellen, beim Versuch den Code zu lösen tragen weder die HTW-Aalen, noch die Entwickler Haftung.</b><br><br>Beim mehrmaligen KLICK auf die OK oder n-Taste, kann der Code ohne große Bemühungen automatisch gelöst werden (durch den Computer).<br><br><font size =+2>Entwickler und Projekt</font><br><br>Entwickler:<br>Christian Homeyer (H8H)<br>Sebastian Orlowski<br>Stefan Schmid<br>Timo Bonzheim<br><br>MasterMind PP-1 wurde unter GNU General Public License veröffentlich,<br>dieses Projekt finden Sie unter GitHub www.github.com/h8h/MasterMind</html>");
        break;
    }
    gD.setVisible(true);
  }

  /**
   * Check if gameDialog has focus
   *
   * @return <code>true</code> gameDialog is open
   * 		 <code>false</code> gameDialog is closed
   */
  public boolean hasDialogFocus() {
	  if(gD == null) { return false; }
	  return gD.isShowing();
  }

  /**
   * Close dialog, if opened
   */
  public void closeDialog() {
	  if(!hasDialogFocus()) { return; }
	  gD.dispose();
  }

  /**
   * Reset and disable game
   */
  protected void disableGame() {
    setEnabledSaveMenu(false);
    removeEnabledColors();
    setEnabledNextTry(false);
  }

  /**
   * Enable game
   */
  protected void enableGame() {
    setEnabledSaveMenu(true);
    setEnabledNextTry(true);
  }

  public void windowActivated(WindowEvent arg0) { }

  /**
   * Check if game is running and if not exit
   *
   * @param arg0 WindowEvent
   * @see #exitGame()
   */
  public void windowClosing(WindowEvent arg0) {
   exitGame();
  }
  public void windowClosed(WindowEvent arg0) { }
  public void windowDeactivated(WindowEvent arg0) { }
  public void windowDeiconified(WindowEvent arg0) { }
  public void windowIconified(WindowEvent arg0) { }
  public void windowOpened(WindowEvent arg0) { }

}
