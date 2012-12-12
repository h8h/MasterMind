package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class hintPane  extends JPanel implements TableCellRenderer {
  JButton[] jb;

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
    setLayout(new FlowLayout());
    String[] hints = (String[])value;
    //First time - create buttons
    if (hints[0] == null ) {
      jb = new JButton[hints.length];
      for (int i = 0; i < jb.length;i++ ){
        add(jb[i]=new JButton());
      }
      return this;
    }
    //Set hit colors /// DOES NOT WORK SO FAR :(
    for(int i=0; i < hints.length; i++) {
      if(hints[i].equals("X")){
        jb[i].setBackground(Color.black);
      } else if(hints[i].equals("O")) {
        jb[i].setBackground(Color.WHITE);
      }
    }
    return this;
  }
}
