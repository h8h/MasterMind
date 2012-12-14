package mastermind_gui;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import mastermind_core.core;

class gameData extends AbstractTableModel{
  private String[] columnNames;
  private int position; // Should be in core
  private boolean celledit=true;
  private core mm_core;

  public gameData(core mm_core) {
   super();
   this.mm_core = mm_core;
   createColumns();
  }

  public void createColumns() {
    columnNames = new String[mm_core.codeLength() + 1]; //codebuttons + hint field panel
    for (int i=0; i < mm_core.codeLength(); i++) {
      columnNames[i] = i +"";
    }
    columnNames[mm_core.codeLength()] = "Hint";
    addTry(); //Add first Try
  }

  public void showCode() {
    mm_core.showCode();
    fireTableDataChanged();
  }

  public void addTry() {
    position = 0; //Rest position;
    mm_core.data.add(0,new Vector<Object>());
    for(int i=0; i < mm_core.codeLength(); i++){
      mm_core.data.get(0).add("");
    }
    mm_core.data.get(0).add(new String[mm_core.codeLength()]); //HINT PANE
    fireTableDataChanged();
  }

  public void setColor(String color) {
    setValueAt(color,0,getPos());
  }

  public int getPos() {
    int i = 0;
    // Search the button, on which the color was deleted
    while (getValueAt(0,i) != null && !((String)getValueAt(0,i)).equals("")) {
      i++;
      if (i > mm_core.codeLength()-1) // All buttons already have colors
        return position++%mm_core.codeLength(); // go on colorwalking...
    }
    return i; //Position of color deleted button
  }

  public void setValueAt(Object value, int row, int col){
    mm_core.data.get(row).setElementAt((String)value, col);
    fireTableCellUpdated(row,col);
  }
  public void setArrayAt(Object value, int row) {
    String[] s = (String[]) value;
    for(int i=0;i < s.length;i++) {
       mm_core.data.get(row).setElementAt(s[i],i);
    }
    fireTableDataChanged();
  }
  public boolean setHint(String[] hints){
    mm_core.data.get(0).setElementAt(hints, getColumnCount()-1);
    fireTableCellUpdated(0,getColumnCount()-1);
    if(hints[hints.length-1] == "X") {
      return true;
    }
    return false;
  }

  public String[] getpinSetting() {
    String[] s = new String[mm_core.codeLength()];
    for (int i=0; i < mm_core.codeLength(); i++) {
      s[i] = (String) getValueAt(0,i);
    }
    return s;
  }
  //Implement Functions
  public int getColumnCount() {
    return columnNames.length;
  }

  public int getRowCount() {
    return mm_core.data.size();
  }

  public String getColumnName(int col) {
    return columnNames[col];
  }

  public Object getValueAt(int row, int col) {
    return ((Vector) mm_core.data.get(row)).get(col);
  }

  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col) {
    return celledit;
  }

  public void setCellEditable(boolean boo){
    celledit = boo;
  }
}
