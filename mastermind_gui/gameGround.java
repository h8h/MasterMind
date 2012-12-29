package mastermind_gui;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import java.awt.Dimension;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import mastermind_gui.mastermind_templates.*;

/**
 * JTable with given column sizes, renderer and editors
 */
class gameGround extends JTable {

  /**
   * Class construction
   */
  public gameGround(gameData gD,String[] enabledColors) {
    super(gD); //Init JTable with Column and Data

    //Table Design
    for(int i=0; i < getColumnCount() - 1;i++) {
      getColumnModel().getColumn(i).setCellRenderer(new pin());
      getColumnModel().getColumn(i).setCellEditor(new pinEditor(enabledColors));
      //setColumnWidth(getColumnModel().getColumn(i),20);
    }
    setColumnWidth(getColumnModel().getColumn(getColumnCount()-1),(getColumnCount()-1)*65);
    getColumnModel().getColumn(getColumnCount()-1).setCellRenderer(new hintPane());
    setRowSelectionAllowed(false);
    setColumnSelectionAllowed(false);
    setCellSelectionEnabled(false);
    getTableHeader().setReorderingAllowed(false);
    setRowHeight(50);
  }

  /**
   * Help class to set column width
   */
  private void setColumnWidth(TableColumn column, int width) {
    column.setPreferredWidth(width);
    column.setMaxWidth(width);
  }
}
