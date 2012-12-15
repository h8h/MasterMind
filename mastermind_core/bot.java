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
  }
}
