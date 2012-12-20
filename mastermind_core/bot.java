package mastermind_core;

public class bot {
  core mm_core;
  boolean makeValidate;

  public bot (core co) {
    mm_core = co;
  }

	protected void setBestColors () {

		int space=0;
    makeValidate = false;
		for(int i=0; i < mm_core.codeLength(); i++) {
			System.out.println(mm_core.getValueAt(i));
			if(mm_core.getValueAt(i) == null || mm_core.getValueAt(i).equals("")) {
				space++;
			}
		}
		if (space==mm_core.codeLength()){	//find a code
			for(int i=0; i < mm_core.codeLength(); i++) {
				System.out.println(mm_core.getValueAt(i));
				if(mm_core.getValueAt(i).equals("")) {
					mm_core.setValueAt(mm_core.getEnabledColors()[i%mm_core.EnabledColorsSize()],i); //Set for examp red pin to empty pinSetting
				}
			}
			if(mm_core.data.size() > 1) {
				String [] s = mm_core.getHintPane(1);
				for(int i = 0; i < s.length; i++) {
					System.out.print(s[i] + " - ");
				}
			}
		}else if (space>0){	//fill the hole
			for(int i=0; i < mm_core.codeLength(); i++) {
				System.out.println(mm_core.getValueAt(i));
				if (mm_core.getValueAt(i).equals("")){
					mm_core.setValueAt(mm_core.getEnabledColors()[0],i); //Set for examp red pin to empty pinSetting
				}
			}
    }else{ //validate
      makeValidate = true;
    }
	}

	protected validator validate() {
    if (!makeValidate) { return new validator(true,"Keine Kommentar, der Bot hat ja schon geholfen");}
		boolean rightColors = true;

		if (mm_core.data.size()==1)
			return new validator(true,"Jede Wahl ist eine gute Wahl.");
		else{
			for (int i = 1; i<mm_core.data.size(); i++){
				for (int j = 0; j<mm_core.codeLength(); j++){
					if (!(mm_core.getValueAt(i,j).equals(mm_core.getValueAt(j))))
						break;
					else if (j==mm_core.codeLength()-1)
						return new validator(false,"Das ist die gleiche Zeile wie Zeile: " + (mm_core.data.size()-i));
				}
			}
			if (!(mm_core.getHintPane(1)[mm_core.codeLength()-1].equals("-"))){
				String[] validateColor = strarraycopy(1);
				for (int i = 0; i<mm_core.codeLength(); i++){
					for (int j = 0; j<mm_core.codeLength(); j++){
						if (mm_core.getValueAt(i).equals(validateColor[j])){
							validateColor[j]="";
							break;
						}
					}
				}
				for (int j = 0; j<mm_core.codeLength();j++){
					if (!validateColor[j].equals(""))
						rightColors = false;
				}
				if (rightColors)
					return new validator(true,"Gute Wahl, solange die Farben vertauscht sind.");
				return new validator(false,"Nicht so gut, da schon alle Farben bekannt sind, sie müssen nur noch vertauscht werden.");
			}
			return new validator(true,"Alles ist gut was der Codefindung dient.");
		}
	}

	protected String[] strarraycopy(int row){
		String[] s = new String[mm_core.codeLength()];
		for (int i = 0; i<mm_core.codeLength(); i++ ){
			s[i]= mm_core.getValueAt(row, i);
		}
		return s;
	}
}
