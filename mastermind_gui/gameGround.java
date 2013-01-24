package mastermind_gui;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import mastermind_gui.mastermind_templates.*;

/**
 * JTable with given column sizes, renderer and editors
 */
@SuppressWarnings("serial")
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
    }
    //Table Preferences
    putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    setColumnWidth(getColumnModel().getColumn(getColumnCount()-1),(getColumnCount()-1)*65);
    getColumnModel().getColumn(getColumnCount()-1).setCellRenderer(new hintPane());
    setRowSelectionAllowed(false);
    setColumnSelectionAllowed(false);
    setCellSelectionEnabled(false);
    getTableHeader().setReorderingAllowed(false);
    setRowHeight(50);
  }

  /**
   * Helper class to set column width
   */
  private void setColumnWidth(TableColumn column, int width) {
    column.setPreferredWidth(width);
    column.setMaxWidth(width);
  }
}
