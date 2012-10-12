package mastermind_core;

import java.util.*;

public class core {
	String[] availableColors =	{"#0000ff","#008000","#00bfff","#483d8b","#696969","#800000","#8b4513","#000000","#FFFFFF","#da70d6"};
	String[] code;

	public core (int codeSize) {
		code = new String[codeSize];
	}

	public String[] generateCode() {
		int randomizeColor = 0;
		Random r = new Random();
		for (int i = 0; i < code.length; i++) {
			randomizeColor = r.nextInt(availableColors.length);		
			code[i] = availableColors[randomizeColor];
		}
		return code;
	}
}


