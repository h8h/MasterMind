package mastermind_gui;

import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;

class gameData extends AbstractTableModel{
   private String[] columnNames = {"First Name",
                                        "Last Name",
                                        "Sport",
                                        "# of Years",
                                        "Vegetarian"};
   private Object[][] data = {
        {"Kathy", "Smith",
         "Snowboarding", new Integer(5),"#123456"},
        {"John", "Doe",
         "Rowing", new Integer(3),"#ff6677" },
        {"Sue", "Black",
         "Knitting", new Integer(2), "#e6e6e6"},
        {"Jane", "White",
         "Speed reading", new Integer(20), "#654321"},
        {"Joe", "Brown",
         "Pool", new Integer(10), "#162534"}
        };

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
      return false;
    }
}
