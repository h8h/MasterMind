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
  private JButton nextTry;
  private JLabel help_text;
  protected options_gui options;
  private JPanel enabledColors;
  private keyboard keylist;
  private Box gameInit;
  private gui game;

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
    keylist = new keyboard(game);
    options = new options_gui(game);

    add(options,BorderLayout.LINE_START);

    nextTry = new JButton ("OK");
    nextTry.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent e) {
        addTry();
      }
    });
    add(nextTry,BorderLayout.LINE_END);
    help_text = new JLabel("Errate den geheimen Code ... | ");
    help_text.setBorder(BorderFactory.createTitledBorder(""));
    help_text.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(help_text,BorderLayout.NORTH);

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent event) {
        setSize(
          Math.max(100, getWidth()),
          Math.max(100, getHeight()));
        }
    });
    addWindowListener(this);
    pack();
    setVisible(true);
  }

  /**
   * Set design and size
   */
  private void initFrame() {
    setPreferredSize(new Dimension(800, 480));
    setMinimumSize(getPreferredSize());
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
  }

  /**
   * Exit game, if it is not running
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
   * @param s text you want to set (overwrite the old text)
   */
  protected void setText(String s) {
    help_text.setText(s);
  }

  /**
   * Get text which is place on left upper corner of the frame
   *
   * @return help text
   */
  protected String getText(){
    return help_text.getText();
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
   * Check how to set code - automatically or manually
   *
   * @return <code>true</code> if code should be set manually<br>
   *         <code>false</code> if code should be set auto
   * @see options_gui#manualCode
   */
  protected boolean isManualCode() {
    return options.manualCode;
  }

  /**
   * Set manualCode in options gui to false, ready to start game
   * @see options_gui#manualCode
   */
  protected void removeManualCode() {
    options.manualCode = false;
  }

  /**
   * Set and activate key listener from given keyboard class
   *
   * @param k keyboard class
   * @see keyboard
   */
  protected void setKeyListener(keyboard k) {
    removeKeyListener();
    keylist = k;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(keylist);
  }

  /**
   * Deactivate key listener
   */
  protected void removeKeyListener() {
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    if (manager != null)
      manager.removeKeyEventDispatcher(keylist);
  }

  /**
   * Reset and disable game
   */
  protected void disableGame() {
    setEnabledSaveMenu(false);
    removeKeyListener();
    removeEnabledColors();
    setEnabledNextTry(false);
  }

  /**
   * Enable game
   */
  protected void enableGame() {
    setEnabledSaveMenu(true);
    setKeyListener(keylist);
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
