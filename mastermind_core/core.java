package mastermind_core;
import java.util.*;

public class core {

  /**
   *  &nbsp;Available colors user can play with
   *  <div style='background-color:#2305e1;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#e02f24;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#e8ec0e;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#28e515;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#ec8b0e;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#7c29a8;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#000000;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#6a1606;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#9ad4f9;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#186d32;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#137167;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#5d5c56;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#cf0cb9;width:10px;height:10px;float:left'></div>
   *  <div style='background-color:#5c7c46;width:10px;height:10px;float:left'></div>
   */
  static final String[] availableColors = {"#2305e1","#e02f24","#e8ec0e","#28e515","#ec8b0e","#7c29a8","#000000","#6a1606","#9ad4f9","#186d32","#137167","#5d5c56","#cf0cb9","#5c7c46"};
  private String[] enabledColors;
  private String[] code;
  private int tries;
  private int triescount;

  /**
   * Data container contains turns and hints (JTable use it)
   */
  public Vector<Vector<Object>> data = new Vector<Vector<Object>>();
  private bot core_bot;

  /**
   * Class construction
   */
  public core (int codeLength,int enabledColorRange, int tries) {
    generateColors(enabledColorRange);
    code = new String[codeLength];
    core_bot = new bot(this);
    generateCode();
    this.tries = tries;
  }

  /**
   * Class construction for creating saved core
   */
  @SuppressWarnings("unchecked")
  public core (Object[] o) {
    generateColors((int)o[1]);
    code = new String[(int)o[0]];
    core_bot = new bot(this);
    code = (String[]) o[2];
    tries = (int) o[3];
    triescount = (int) o[4];
    data = (Vector<Vector<Object>>) o[5];
  }

  /**
   * Generate colors which users are able to play with
   *
   * @param enabledColorRange select colors in availableColors from 0 til enabledColorRange
   * @see #availableColors
   */
  private void generateColors(int enabledColorRange) {
    enabledColors = new String[enabledColorRange];
    for (int i = 0; i < enabledColorRange; i++) {
      enabledColors[i] = availableColors[i];
    }
  }

  /**
   * Generate randomize secret code
   */
  public void generateCode() {
    int randomizeColor = 0;
    Random r = new Random();
    for (int i = 0; i < code.length; i++) {
      randomizeColor = r.nextInt(enabledColors.length);
      code[i] = enabledColors[randomizeColor];
    }
  }

  /**
   *  Get color array generated by <code>initColors(int)</code>
   *
   *  @return array with hex-color strings
   *  @see #generateColors(int)
   *  @see #availableColors
   */
	public String[] getEnabledColors() {
		return enabledColors;
	}

  /**
   * Return size of enabledColors
   *
   * @return get enabled colors size
   * @see #enabledColors
   */
  public int getEnabledColorsSize() {
    return enabledColors.length;
  }

  /**
   *  Return secret code size
   *
   *  @return code size
   *  @see #generateCode()
   */
  public int getCodeSize() {
    return code.length;
  }

  /**
   *  Return max tries
   *
   *  @return max tries
   */
  public int getTries() {
    return tries;
  }

  /**
   * Return number of left tries
   *
   * @return left tries
   */
  public int leftTries(){
    return tries-triescount-1;
  }

  /**
   * Check if user is allowed to make a new try
   *
   * @return <code>true</code> make new try<br>
   *         <code>false</code> game over :(
   */
  public boolean checkTries() {
    if(++triescount == tries) {
      return true;
    }
    return false;
  }

  /**
   * Add secret code to <code>data</code> (if user lost game)
   *
   * @see #data
   */
  public void showCode() {
    data.add(0, new Vector<Object>());
    for (int i=0; i < code.length; i++) {
      data.get(0).add(code[i]);
    }
    data.get(0).add(new String[code.length]);
  }

  /**
   * Check whether the user set the right color at the right place or the right color at the wrong place
   *
   * @return <code>X</code> for the right color at the right place<br>
   *         <code>O</code> for the right color at the wrong place
   */
  public String[] checkColor (){
    String[] codeColors = code.clone();
    System.arraycopy(code, 0, codeColors, 0, codeColors.length);
    String[] userColors = getDataArray();

    String[] check = new String[codeColors.length];
    int checkpos = 0;

    // Color check funktion
    for(int i=0 ; i<userColors.length ; i++){
      if(userColors[i].equals(codeColors[i])){
        check[checkpos++] = "X";
        userColors[i] = "UX";
        codeColors[i] = "CX";
      }
    }

    for(int i=0 ; i<userColors.length ; i++){
      for(int j=0 ; j<codeColors.length ; j++){
        if(userColors[i].equals("UX"))
          break;
        else if(userColors[i].equals(codeColors[j])){
          check[checkpos++] = "O";
          userColors[i] = "UX";
          codeColors[j] = "CX";
          break;
        }
        else if(j==codeColors.length-1){
          userColors[i] = "UX";
          check[checkpos++]="-";
          break;
        }
      }
    }
    return check;
  }

  /**
   * Get color setting (turn) from first row of <code>data</code>
   *
   * @return String array with hex colors
   */
  protected String[] getDataArray () {
    return getDataArray(0);
  }

  /**
   * Get color setting (turn) from given row of <code>data</code>
   *
   * @param row which you want to get the color setting from
   * @return array with hex colors
   */
  protected String[] getDataArray (int row) {
    if (row < data.size()) {
      String[] s = new String[getCodeSize()];
      for(int i=0; i < s.length;i++) {
       s[i] = getValueAt(row, i);
      }
      return s;
    }
    return null;
  }

  /**
   * Get value (HEX Color) from given column of data at first row
   *
   * @param col column which you want to get the value from
   * @return hex color at this position<br>
   *         or <code>null</code> if out-of-range
   */
  protected String getValueAt(int col) {
    return getValueAt(0,col);
  }

  /**
   * Get value (HEX Color) from given column and given row of data
   *
   * @param row position for getting value
   * @param col (column) position for getting value
   * @return hex color at this position<br>
   *         or <code>null</code> if out-of-range
   */
  protected String getValueAt(int row, int col) {
    if (row < data.size() && col < getCodeSize())
      return (String) ((Vector<Object>) data.get(row)).get(col);
    return null;
  }

  /**
   *
   * Get the hex color columns as a string array
   *
   * @param row
   * @return hex color as a string array
   */

  protected String [] getArAt(int row) {
    String [] s = new String [getCodeSize()];
    for (int i = 0 ; i < getCodeSize();i++){
      s[i]= getValueAt( row, i);
    }
    return s;
  }

  /**
   *
   * Set an hex color array to first row
   *
   * @param colors as an String array
   * @see #setValueAt
   */
  protected void setArAt(String[] colors) {
    for (int i = 0; i < getCodeSize();i++) {
     setValueAt(colors[i], i);
    }
  }

  /**
   * Get hints (right color X / right/wrong place O) from given row
   *
   * @param row position for getting array
   * @return array with hints
   * @see #checkColor()
   */
  protected String[] getHintPane(int row) {
    if (row < data.size())
      return (String []) ((Vector<Object>) data.get(row)).get(getCodeSize());
    return null;
  }

  /**
   * Set value (HEX Color) at first row and given column
   *
   * @param value hex color to be set
   * @param col (column) position to set value less than <code>getCodeSize()</code>
   */
  public void setValueAt(String value, int col){
    if (value !=null && col < getCodeSize())
      data.get(0).setElementAt((String)value, col);
  }

  /**
   * Export important core objects (for saving purpose)
   *
   * @return secret code size<br>enabled color range<br>code colors<br>maximum tries<br>number of done tries<br>data with all turns and hints<br>
   */
  public Object[] makePKG() {
    Object[] o = {getCodeSize(), getEnabledColorsSize(), code, tries, triescount, data};
    return o;
  }

  /**
   * Let the Bot check the turn (before creating a new try)
   */
  public void doitBot() {
    core_bot.setBestColors();
  }

  /**
   * Validate the turn
   *
   * @return <code>true</code> plus help text - go on playing (next try)<br>
   *         <code>false</code> plus help text - stop current turn - correct your fault
   * @see validator
   */
  public validator validate() {
    return core_bot.validate();
  }
}
