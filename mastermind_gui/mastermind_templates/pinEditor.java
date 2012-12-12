package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class pinEditor extends AbstractCellEditor implements TableCellEditor,ActionListener {
  JButton button;
  String[] enabledColors;
  int position=-1;

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
    button.setBackground(getButtonBackground((String)value));
    if (position == -1 ) { //First run
      int i = 0;
      while (!enabledColors[i].equals((String)value)){ //Search current colorwalk position
        i++;
        position++;
      }
      position++; //Next position from current Color
    }
    return button;
  }

  public Object getCellEditorValue() {
    if (button.getBackground()==null||position > enabledColors.length-1) //No BackgroundColor, maybe to enable BOT
      return null;
    return enabledColors[position];
  }

  public Color getButtonBackground(String value) {
     if (value == null) {
      return null;
    } else {
      return Color.decode((String) value);
    }

  }
}
