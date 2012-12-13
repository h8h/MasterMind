package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class pinEditor extends AbstractCellEditor implements TableCellEditor,ActionListener {
  JButton button;
  String[] enabledColors;
  int position=0;

  public pinEditor(String[] enabledColors) {
    button = new JButton();
    button.addActionListener(this);
    button.setBorderPainted(false);
    this.enabledColors = enabledColors;
  }

  public void actionPerformed(ActionEvent e) {
    button.setBackground(getButtonBackground(changeColor()));
    fireEditingStopped(); //Make the renderer reappear.
  }
  public String changeColor(){
    if(position > enabledColors.length-1) {
      position = 0;
      return null; // NO BackgroundColor, maybe to enable BOT
    }
    return enabledColors[position++];
  }
  //Implement the one method defined by TableCellEditor.
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

  public Object getCellEditorValue() {
    if (button.getBackground()==null||position > enabledColors.length-1) //No BackgroundColor, maybe to enable BOT
      return null;
    return enabledColors[position];
  }

  public Color getButtonBackground(String value) {
     if (value == null || value.equals("")) {
      return null;
    } else {
      return Color.decode((String) value);
    }

  }
}
