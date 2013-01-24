package mastermind_gui;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import mastermind_core.core;

/**
 * Bind core data to Table
 *
 * @see mastermind_core.core#data
 */
@SuppressWarnings("serial")
class gameData extends AbstractTableModel{
  private String[] columnNames;
  private int position;
  private boolean celledit=true;
  private core mmCore;

  /**
   * Class construction
   *
   * @param mmCore
   */
  public gameData(core mmCore) {
    super();
    this.mmCore = mmCore;
    createColumns();
    if(getRowCount() == 0) { //New Game or game loaded?
      addTry(); //Add first Try
    }
  }

  /**
   * Create columns: code index numbs + hintPane
   */
  public void createColumns() {
    columnNames = new String[mmCore.getCodeSize() + 1]; //code size + hintPane
    for (int i=0; i < mmCore.getCodeSize(); i++) {
      columnNames[i] = (i +1)+"";
    }
    columnNames[mmCore.getCodeSize()] = "";
  }

  /**
   * Show secret code (if user lost game)
   */
  public void showCode() {
    mmCore.showCode();
    fireTableDataChanged();
  }

  /**
   * Add new try (next turn)
   */
  public void addTry() {
    position = 0; //Rest position;
    mmCore.data.add(0,new Vector<Object>());
    for(int i=0; i < mmCore.getCodeSize(); i++){
      mmCore.data.get(0).add("");
    }
    mmCore.data.get(0).add(new String[mmCore.getCodeSize()]); //HINT PANE
    fireTableDataChanged();
  }

  /**
   * Set value (HEX Color) at first row and given column in <code>data</code>
   *
   * @param color HEX Color
   * @param column position to set value less than code size
   */
  protected void setColorAt(String color, int column) {
    setValueAt(color,0,column);
  }

  /**
   * Set color to next free (empty) position
   *
   * @param color HEX Color
   * @see #getPos()
   */
  public void setColor(String color) {
    setValueAt(color,0,getPos());
  }

  /**
   * Remove value (HEX Color) at first row and given column in <code>data</code>
   *
   * @param column position to delete color from
   */
  protected void removeColor(int column) {
    setValueAt("",0,column);
  }

  /**
   * Search next free (empty) position
   *
   * @return next free position index
   */
  public int getPos() {
    int i = 0;
    // Search the button, on which the color was deleted
    while (getValueAt(0,i) != null && !((String)getValueAt(0,i)).equals("")) {
      i++;
      if (i > mmCore.getCodeSize()-1) // All buttons already have colors
        return position++%mmCore.getCodeSize(); // go on colorwalking...
    }
    return i; //Position of color deleted button
  }

  /**
   * Set value (HEX Color) at given (first) row and given column on data
   *
   * @param value hex color to be set
   * @param row row position almost 0 (value not used, but needed cause of overwriting)
   * @param col (column) position to set value less than code size
   */
  public void setValueAt(Object value, int row, int col){
    mmCore.setValueAt((String) value, col);
    fireTableCellUpdated(0,col);
  }

  /**
   * Set hint array at first row on data and check if user has won
   *
   * @param hints array containing hints (X / O)
   * @return <code>true</code> user win<br>
   *         <code>false</code> go on playing
   * @see mastermind_core.core#checkColor()
   */
  public boolean setHint(String[] hints){
    mmCore.data.get(0).setElementAt(hints, getColumnCount()-1);
    fireTableCellUpdated(0,getColumnCount()-1);
    if(hints[hints.length-1] == "X") { //check if user has won
      return true;
    }
    return false;
  }

  /**
   * Number of columns: Code Size + 1 (HintPane)
   *
   * @return number of columns
   */
  public int getColumnCount() {
    return columnNames.length;
  }

  /**
   * Turns the user has made
   *
   * @return number of tries
   */
  public int getRowCount() {
    return mmCore.data.size();
  }

  /**
   * Make table read only (if user win / lost)
   *
   * @param boo <code>true</code> go on playing<br>
   *            <code>false</code> game over
   */
  public void setCellEditable(boolean boo){
    celledit = boo;
  }

  /**
   * Get Value at given row and given column
   *
   * @param row position for getting value
   * @param col (column) position for getting value
   * @return hex color at this position<br>or <code>null</code> if out-of-range
   * @see mastermind_core.core#getValueAt(int, int)
   */
  public Object getValueAt(int row, int col) {
    return mmCore.data.get(row).get(col);
  }

  //Implement/Overwrite Functions
  public String getColumnName(int col) {
    return columnNames[col];
  }

  public Class<? extends Object> getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col) {
    return celledit;
  }
}
