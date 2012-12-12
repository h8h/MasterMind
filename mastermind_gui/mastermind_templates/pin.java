package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class pin extends JButton implements TableCellRenderer,ActionListener {
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
    if(value == null || value.equals("")){
      setBackground(null);
      return this;
    }

    // Style Button
    setBackground(Color.decode((String) value));
    return this;
  }
   public void actionPerformed(ActionEvent e) {
    System.out.println("UIII");
   }
}
