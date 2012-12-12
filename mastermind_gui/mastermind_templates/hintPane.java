package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class hintPane implements TableCellRenderer {
  JButton[] jb;

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
    String[] hints = (String[])value;
    //First time - create buttons
    if (hints[0] == null ) {
      jb = new JButton[hints.length];
      for (int i = 0; i < jb.length;i++ ){
        jb[i]=new JButton();
      }
      return new JPanel();
    }
    JPanel jp = new JPanel();
    jp.setLayout(new FlowLayout());
    int jbpos = 0;
    for(int i=0; i < hints.length; i++) {
      if(hints[i].equals("X")){
        jb[jbpos].setBackground(Color.black);
        jp.add(jb[jbpos]);
        jbpos++;
      } else if(hints[i].equals("O")) {
        jb[jbpos].setBackground(Color.red);
        jp.add(jb[jbpos]);
        jbpos++;
      }
    }
    for(int j=jbpos; j < jb.length; j++) {
        jp.add(jb[j]);
    }
    return jp;
  }
}
