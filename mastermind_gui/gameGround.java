package mastermind_gui;

import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import mastermind_gui.mastermind_templates.*;

public class gameGround extends JTable {

  public gameGround(gameData gD,String[] enabledColors) {
    super(gD); //Init JTable with Column and Data

    //Table DesignsetRowHeight(int row_height)
    setPreferredScrollableViewportSize(new Dimension(500,500));
    for(int i=0; i < getColumnCount() - 1;i++) {
      getColumnModel().getColumn(i).setCellRenderer(new pin());
      getColumnModel().getColumn(i).setCellEditor(new pinEditor(enabledColors));
    }
    getColumnModel().getColumn(getColumnCount()-1).setCellRenderer(new hintPane());
    setRowHeight(50);
  }
}
