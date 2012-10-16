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
        
        public static String[] color_check (String[] codeColor, String[] userColor){
        
            String[] codeColors = codeColor;
            String[] userColors = userColor;
        
            String[] check = new String[codeColors.length];
            int checkpos = 0;
        
            // Auf Farbe+Position prüfen
            for(int i=0 ; i<userColors.length ; i++){
                if(userColors[i].equals(codeColors[i])){
                    check[checkpos++] = "X";
                    userColors[i] = "UX";
                    codeColors[i] = "CX";
                }
            }

            // Nur auf Farbe prüfen
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
    }


