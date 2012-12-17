package mastermind_core;

public class bot {
  core mm_core;

  public bot (core co) {
    mm_core = co;
  }

	protected void setBestColors () {
		
		int space=0;
	
		for(int i=0; i < mm_core.codeLength(); i++) {
			System.out.println(mm_core.getValueAt(i));
			if(mm_core.getValueAt(i) == null || mm_core.getValueAt(i).equals("")) {
				space++;
			}
		}
		if (space==mm_core.codeLength()){
			for(int i=0; i < mm_core.codeLength(); i++) {
				System.out.println(mm_core.getValueAt(i));
				if(mm_core.getValueAt(i) == null || mm_core.getValueAt(i).equals("")) {
					mm_core.setValueAt(mm_core.getEnabledColors()[1],i); //Set for examp red pin to empty pinSetting
				}
			}
			if(mm_core.data.size() > 1) {
				String [] s = mm_core.getHintPane(1);
				for(int i = 0; i < s.length; i++) {
					System.out.print(s[i] + " - ");
				}
			}
		}else{
			for(int i=0; i < mm_core.codeLength(); i++) {
				System.out.println(mm_core.getValueAt(i));
				if(mm_core.getValueAt(i) == null || mm_core.getValueAt(i).equals("")) {
					mm_core.setValueAt(mm_core.getEnabledColors()[2],i); //Set for examp red pin to empty pinSetting
				}
			}
		}
	}
}
