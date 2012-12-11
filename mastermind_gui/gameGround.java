package mastermind_gui;

import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import mastermind_gui.mastermind_templates.pin;

public class gameGround extends JTable {

  public gameGround() {
    super(new gameData());
    setPreferredScrollableViewportSize(new Dimension(500,70));
    getColumnModel().getColumn(4).setCellRenderer(new pin());
  }
}
