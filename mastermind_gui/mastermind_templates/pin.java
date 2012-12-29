package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Render for hex color in a single field - returns button
 */
public class pin extends JButton implements TableCellRenderer {
  /**
   * Get color (value), put it as background color on button and return button
   */
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
    if(value == null || value.equals("")){
      setBackground(null);
      return this;
    }

    // Style Button
    setBackground(Color.decode((String) value));
    return this;
  }
}
