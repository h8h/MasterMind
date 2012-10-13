package mastermind_core;

import java.util.*;

public class core {
	String[] availableColors =	{"#0000ff","#008000","#00bfff","#483d8b","#696969","#800000","#8b4513","#000000","#FFFFFF","#da70d6"};
	String[] usedColors;
	String[] code;

	public core (int codeSize) {
		initColors(codeSize);
	}

	private void initColors(int codeSize) {
		usedColors = new String[codeSize];
		code = new String[codeSize / 2];
		for (int i = 0; i < codeSize; i++) {
			usedColors[i] = availableColors[i];	
		}
	}

	public String[] generateCode() {
		int randomizeColor = 0;
		Random r = new Random();
		for (int i = 0; i < code.length; i++) {
			randomizeColor = r.nextInt(usedColors.length);		
			code[i] = usedColors[randomizeColor];
		}
		return code;
	}
	
	public String[] getUsedColors() {
		return usedColors;
	}
}


