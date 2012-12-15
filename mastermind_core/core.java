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

	public core (int codeLength,int enabledColorRange, int tries) {
		initColors(codeLength,enabledColorRange);
    core_bot = new bot(this);
    generateCode();
    this.tries = tries;
  }

  public core (Object[] o) {
    initColors((int)o[0],(int)o[1]);
    core_bot = new bot(this);
    code = (String[]) o[2];
    tries = (int) o[3];
    triescount = (int) o[4];
    data = (Vector<Vector>) o[5];
  }

	private void initColors(int codeLength,int enabledColorRange) {
		enabledColors = new String[enabledColorRange];
		code = new String[codeLength];
		for (int i = 0; i < enabledColorRange; i++) {
			enabledColors[i] = availableColors[i];
		}
	}

	public void generateCode() {
		int randomizeColor = 0;
		Random r = new Random();
		for (int i = 0; i < code.length; i++) {
			randomizeColor = r.nextInt(enabledColors.length);
			code[i] = enabledColors[randomizeColor];
		}
	}

	public String[] getEnabledColors() {
		return enabledColors;
	}

  public int EnabledColorsSize() {
    return enabledColors.length;
  }

  public int codeLength() {
    return code.length;
  }

  public int getTries() {
    return tries;
  }

  public boolean checkTries() {
    if(++triescount == tries) {
      return true;
    }
    return false;
  }

  public void showCode() {
    data.add(0, new Vector<Object>());
    for (int i=0; i < code.length; i++) {
      data.get(0).add(code[i]);
    }
    data.get(0).add(new String[code.length]);
  }

  public String[] color_check (){
    String[] codeColors = code.clone(); //Not nice, but it works
    System.arraycopy(code, 0, codeColors, 0, codeColors.length); //Not nice, but it works
    core_bot.setBestColors();
    String[] userColors = getDataArray();

    String[] check = new String[codeColors.length];
    int checkpos = 0;

    // Auf Farbe+Position pruefen
    for(int i=0 ; i<userColors.length ; i++){
      if(userColors[i].equals(codeColors[i])){
        check[checkpos++] = "X";
        userColors[i] = "UX";
        codeColors[i] = "CX";
      }
    }

    // Nur auf Farbe pruefen
    loop1: for(int i=0 ; i<userColors.length ; i++){
      loop2: for(int j=0 ; j<codeColors.length ; j++){
        if(userColors[i].equals("UX"))
          break loop2;
        else if(userColors[i].equals(codeColors[j])){
          check[checkpos++] = "O";
          userColors[i] = "UX";
          codeColors[j] = "CX";
          break loop2;
        }
        else if(j==codeColors.length-1){
          userColors[i] = "UX";
          check[checkpos++]="-";
          break loop2;
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

 protected void setValueAt(String value, int col){
    data.get(0).setElementAt((String)value, col);
  }

  public Object[] makePKG() {
    Object[] o = {code.length, enabledColors.length, code, tries, triescount, data};
    return o;
  }
}
