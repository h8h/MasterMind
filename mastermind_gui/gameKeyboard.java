package mastermind_gui;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import mastermind_gui.gameArea.gameDialogSelector;

/**
 * Key bindings for a - key, d - key, f1, strg+a, strg+n
 */
class gameKeyboard implements KeyEventDispatcher{
  private gui g;
  private boolean d;
  private boolean a;
  private int first_value=-1;
  private int second_value=-1;
  private String tmpHelpText="";
  /**
   * Class construction
   */
  public gameKeyboard (gui g) {
    this.g = g;
  }

  /**
   * Call by java keyevent
   *
   * @param e keycode
   */
  public boolean dispatchKeyEvent(KeyEvent e) {
    if (e.getID() == KeyEvent.KEY_PRESSED) {
      keypressed(e);
    }
    return false;
  }

  /**
   * Check which key is pressed and do action
   *
   * @param e keycode
   */
  private void keypressed (KeyEvent e) {
    if(g.gameRunningDialog) { return; } //If gameRunningDialog is shown, don't accept any keys
	if(g.hasDialogFocus()) {  //Only enter, if gameDialog is opened
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			g.closeDialog();
		return;
	}
	// Show Help Dialog
	if(e.getKeyCode() == KeyEvent.VK_F1) {
	  g.showDialog(gameDialogSelector.HELP);
      return;
	}
	// Show Info Dialog
	if(e.getKeyCode() == KeyEvent.VK_A && e.isControlDown()) {
      g.showDialog(gameDialogSelector.INFO);
      return;
	}
	// CTRL + N -> New Game
	if(e.getKeyCode() == KeyEvent.VK_N && e.isControlDown()) {
	  	g.newGame();
	   	return;
	}
	if(g.gameFinished()) { return; }
    //Only enter, if game is not finished
	// N pressed -> New Try
    if(e.getKeyCode() == KeyEvent.VK_N ) {
      resetValues();
      g.addTry();
      return;
    }

    //Add color first_value to column second_value
    if (a) {
      if(setValues(e) && first_value > -1 && second_value > -1) {
        g.setColorAt(first_value-1,second_value-1);
        resetValues();
      }
    }

    // Delete color from column first_value
    if (d) {
      setValues(e);
      if(first_value > -1)
        g.removeColor(first_value-1);
      resetValues();
    }

    if (tmpHelpText.equals("")) {
      tmpHelpText = g.getHapticFeedback();
    }

    //Add / Delete button is pressed
    switch(e.getKeyCode()) {
      case KeyEvent.VK_D:
                 g.setHapticFeedback(tmpHelpText + " - D: Lösche - Farbe 1-10-Q-R");
                 a = false;
                 d = true;
                 break;
      case KeyEvent.VK_A:
                 g.setHapticFeedback(tmpHelpText + " - A: Hinzufügen - Farbe 1-10-Q-R/Spalte 1-10");
                 a = true;
                 d = false;
                 break;
    }

  }

  /**
   * Set button and column number<br>
   * and check if key action is complete
   *
   * @param e keycode
   * @return <code>true</code> user pressed three (a or d / twice 1 - 0 + q-r ) keys.
   *         <code>false</code> otherwise
   */
  private boolean setValues(KeyEvent e) {
    int key = (int) e.getKeyChar();
    switch (key) { //convert Q W E R T to integer
        case  48: key = 10;
                  break;
        case 113: key = 11;
                  break;
        case 119: key = 12;
                  break;
        case 101: key = 13;
                  break;
        case 114: key = 14;
                  break;
    }
    if( (key > 48 && key < 58) || (key > 9 && key < 15)) {
      if (first_value == -1) {
        first_value = (key>9&&key<15) ? key : key  - 48;
      } else if (second_value == -1 ) {
        second_value = (key>9&&key<15) ? key: key - 48;
        return true;
      }
    } else { //Reject wrong key
      resetValues();
    }
    return false;
  }

  /**
   * Reset values, if user presses other key
   */
  private void resetValues() {
    first_value = -1;
    second_value = -1;
    a = false;
    d = false;
    if(!tmpHelpText.equals(""))
      g.setHapticFeedback(tmpHelpText);
    tmpHelpText = "";
  }
}
