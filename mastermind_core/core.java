package mastermind_core;

import java.util.*;

public class core {
	static final String[] availableColors =	{"#2305e1","#e02f24","#e8ec0e","#28e515","#ec8b0e","#7c29a8","#000000","#6a1606","#9ad4f9","#186d32","#137167","#5d5c56","#cf0cb9","#5c7c46"};
	private String[] enabledColors;
	private String[] code;
  private int tries;
  private int triescount;
  public Vector<Vector> data = new Vector<Vector>();
  private bot core_bot;

  /**
   * Class constructor
   */
	public core (int codeLength,int enabledColorRange, int tries) {
		initColors(enabledColorRange);
		code = new String[codeLength];
    core_bot = new bot(this);
    generateCode();
    this.tries = tries;
  }
  /**
   * Class constructor for creating saved core
   */
  public core (Object[] o) {
    initColors((int)o[1]);
    code = new String[(int)o[0]];
    core_bot = new bot(this);
    code = (String[]) o[2];
    tries = (int) o[3];
    triescount = (int) o[4];
    data = (Vector<Vector>) o[5];
  }
  /**
   * Init colors witch users are allowed to play with
   *
   * @param enabledColorRange used colors in availableColors array from 0 til enabledColorRange
   */
	private void initColors(int enabledColorRange) {
		enabledColors = new String[enabledColorRange];
		for (int i = 0; i < enabledColorRange; i++) {
			enabledColors[i] = availableColors[i];
		}
	}
  /**
   * Generate randomize secret Code
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
   *  Get EnabledColors
   *
   *  @see #initColors(int)
   *  @return String[] contains Colors to play with
   */
	public String[] getEnabledColors() {
		return enabledColors;
	}

  /**
   *  Return size of enabledColors array
   *
   * @see #initColors(int)
   * @return int
   */
  public int EnabledColorsSize() {
    return enabledColors.length;
  }

  /**
   *  Return size of code
   *
   *  @see #generateCode()
   *  @return int
   */
  public int codeLength() {
    return code.length;
  }
  /**
   *  Return max tries
   *
   *  @return tries
   */
  public int getTries() {
    return tries;
  }

  /**
   * Check if user is allowed to make a new try
   *
   * @return boolean true, new try
   */
  public boolean checkTries() {
    if(++triescount == tries) {
      return true;
    }
    return false;
  }

  /**
   * Add Code to database
   */
  public void showCode() {
    data.add(0, new Vector<Object>());
    for (int i=0; i < code.length; i++) {
      data.get(0).add(code[i]);
    }
    data.get(0).add(new String[code.length]);
  }

  public String[] color_check (){
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
          break ;
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

  protected String[] getDataArray () {
    String[] s = new String[codeLength()];
    for(int i=0; i < s.length;i++) {
     s[i] = getValueAt(i);
    }
    return s;
  }
  protected String getValueAt(int col) {
    return (String) ((Vector) data.get(0)).get(col);
  }

  protected String getValueAt(int row, int col) {
    return (String) ((Vector) data.get(row)).get(col);
  }

  protected String[] getHintPane(int row) {
    return (String []) ((Vector) data.get(row)).get(codeLength());
  }

  protected void setValueAt(String value, int col){
    data.get(0).setElementAt((String)value, col);
  }

  public Object[] makePKG() {
    Object[] o = {code.length, enabledColors.length, code, tries, triescount, data};
    return o;
  }

  public void doitBot() {
    core_bot.setBestColors();
  }

  public validator validate() {
    return core_bot.validate();
  }
}
