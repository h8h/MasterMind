package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Editor for changing background color on table - returns button
 */
public class pinEditor extends AbstractCellEditor implements TableCellEditor,ActionListener {
  JButton button;
  String[] enabledColors;
  int position=0;

  /**
   * Class construction
   *
   * @param enabledColors get available colors to switch through
   * @see mastermind_core.core#generateColors(int)
   */
  public pinEditor(String[] enabledColors) {
    button = new JButton();
    button.addActionListener(this);
    button.setBorderPainted(false);
    this.enabledColors = enabledColors;
  }

  /**
   * Change color on click
   */
  public void actionPerformed(ActionEvent e) {
    button.setBackground(getButtonBackground(changeColor()));
    fireEditingStopped(); //Make the renderer reappear.
  }

  /**
   * Change color to next color in enabledColors
   *
   * @return next color
   */
  public String changeColor(){
    if(position > enabledColors.length-1) {
      position = 0;
      return null; // NO BackgroundColor, maybe to enable BOT
    }
    return enabledColors[position++];
  }

  /**
   * Returns the new color
   */
  public Component getTableCellEditorComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               int row,
                                               int column) {
    if(row!=0) {return null;}
      int i = 0;
      position=0;
      while (!enabledColors[i].equals((String)value)){ //Search current colorwalk position
        i++;
        position++;
        if (i > enabledColors.length-1) {
          position = enabledColors.length;
          break;
        }
      }

    button.setBackground(getButtonBackground((String)value));
    return button;
  }

  /**
   * Get current hex color (from position)
   *
   * @return current hex color
   */
  public Object getCellEditorValue() {
    if (button.getBackground()==null||position > enabledColors.length-1) //No BackgroundColor, maybe to enable BOT
      return null;
    return enabledColors[position];
  }

  /**
   * Get current hex color (from button / value in table)
   *
   * @return current hex color
   */
  public Color getButtonBackground(String value) {
     if (value == null || value.equals("")) {
      return null;
    } else {
      return Color.decode((String) value);
    }
  }
}
