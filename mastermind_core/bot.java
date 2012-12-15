package mastermind_core;

public class bot {
  core mm_core;

  public bot (core co) {
    mm_core = co;
  }

  protected void setBestColors () {
    for(int i=0; i < mm_core.codeLength(); i++) {
      if(mm_core.getValueAt(i) == null || mm_core.getValueAt(i).equals("")) {
        mm_core.setValueAt(mm_core.getEnabledColors()[1],i); //Set for examp red pin to empty pinSetting
      }
    }
    //Do some DEBUG Function Tests
    if(mm_core.data.size() > 1) {
    System.out.print("Farbe an Position 1 / 2" + mm_core.getValueAt(1,0));
    String [] s = mm_core.getHintPane(1);
      for(int i = 0; i < s.length; i++) {
        System.out.print(s[i] + " - ");
      }
    }
  }
}
