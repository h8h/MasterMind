package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class hintPane implements TableCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
    String[] hints = (String[])value;
    if (hints[0] == null) { return new JPanel();}
    JPanel jp = new JPanel();
    jp.setLayout(new FlowLayout());
    int jbpos = 0;
    for(int i=0; i < hints.length; i++) {
      JButton jb = new JButton();
      if(hints[i].equals("X")){
        jb.setBackground(Color.black);
        jp.add(jb);
        jbpos++;
      } else if(hints[i].equals("O")) {
        jb.setBackground(Color.red);
        jp.add(jb);
        jbpos++;
      }
    }
    //Create Buttons which are not set
    for(int j=jbpos; j < hints.length; j++) {
      jp.add(new JButton());
    }
    return jp;
  }
}
