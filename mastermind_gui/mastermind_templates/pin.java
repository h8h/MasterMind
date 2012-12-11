package mastermind_gui.mastermind_templates;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class pin extends JButton implements TableCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
  {
    setBackground(Color.decode((String) value));
    setText((String) value );    // Text setzen, hier z.B. 1 2
    return this;
  }
}
