package mastermind_gui;

import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;
import java.util.Vector;

class gameData extends AbstractTableModel{
  private String[] columnNames;
  private Vector<Vector> data = new Vector<Vector>(); // Should be in core
  private int codeLength; // Should be in core
  private int position; // Should be in core

  public gameData(int codeLength) {
   super();
   this.codeLength = codeLength;
   createColumns();
  }

  public void createColumns() {
    columnNames = new String[codeLength + 1]; //codebuttons + hint field panel
    for (int i=0; i < codeLength; i++) {
      columnNames[i] = i +"";
    }
    columnNames[codeLength] = "Hint";
    addTry(); //Add first Try
  }

  public void addTry() {
    position = 0; //Rest position;
    data.add(0,new Vector<Object>());
    for(int i=0; i < codeLength; i++){
      data.get(0).add("");
    }
    data.get(0).add(new String[codeLength]); //HINT PANE
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
      if (i > codeLength-1) // All buttons already have colors
        return position++%codeLength; // go on colorwalking...
    }
    return i; //Position of color deleted button
  }

  public void setValueAt(Object value, int row, int col){
    data.get(row).setElementAt((String)value, col);
    fireTableCellUpdated(row,col);
  }

  public void setHint(String[] hints){
    data.get(0).setElementAt(hints, getColumnCount()-1);
    fireTableCellUpdated(0,getColumnCount()-1);
  }

  public String[] getpinSetting() {
    String[] s = new String[codeLength];
    for (int i=0; i < codeLength; i++) {
      s[i] = (String) getValueAt(0,i);
    }
    return s;
  }
  //Implement Functions
  public int getColumnCount() {
    return columnNames.length;
  }

  public int getRowCount() {
    return data.size();
  }

  public String getColumnName(int col) {
    return columnNames[col];
  }

  public Object getValueAt(int row, int col) {
    return ((Vector) data.get(row)).get(col);
  }

  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col) {
    return true;
  }
}
