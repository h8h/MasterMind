package mastermind_gui;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
class keyboard implements KeyEventDispatcher{
  gui g;
  boolean d;
  boolean a;
  int first_value=-1;
  int second_value=-1;

  public keyboard (gui g) {
    this.g = g;
  }

  public boolean dispatchKeyEvent(KeyEvent e) {
    if (e.getID() == KeyEvent.KEY_PRESSED) {
      keypressed(e);
    }
    return false;
  }

  public void keypressed (KeyEvent e) {
    if (a) {
      if(setValues(e)) {
          System.out.println("Hinzufügen: " + first_value + " zu " + second_value);
      resetValues();
      }
    }

    if (d) {
      setValues(e);
      System.out.println("Entfernen Farbe:" + first_value);
      resetValues();
    }

    switch(e.getKeyCode()) {
      case KeyEvent.VK_D: a = false;
                 d = true;
                 break;
      case KeyEvent.VK_A: a = true;
                 d = false;
                 break;
    }

  }

  public boolean setValues(KeyEvent e) {
    int key = (int) e.getKeyChar();
    switch (key) {
        case 113: key = 11;
                  break;
        case 119: key = 12;
                  break;
        case 101: key = 13;
                  break;
        case 114: key = 14;
                  break;
    }
    if( (key > 47 && key < 58) || (key > 10 && key < 15)) {
      if (first_value == -1) {
        first_value = (key>10&&key<15) ? key : key  - 48;
      } else if (second_value == -1 ) {
        second_value = (key>10&&key<15) ? key: key - 48;
        return true;
      }
    } else { //Reject
      resetValues();
    }

    return false;
  }

  public void resetValues() {
    first_value = -1;
    second_value = -1;
    a = false;
    d = false;
  }
}
