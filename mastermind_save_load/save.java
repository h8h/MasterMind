package mastermind_save_load;
import java.io.*;
import mastermind_core.core;

class save {
  String filename;
  core mastermindCore;

  public save (String file, core mastermind_game) {
    filename = file;
  }

  private void savefile() {
    try {
      FileOutputStream saveFile = new FileOutputStream(filename);
      ObjectOutputStream save = new ObjectOutputStream(saveFile);
      save.writeObject("GUDE ABEND!");
      save.close();
    } catch(Exception exc){
      exc.printStackTrace(); // If there was an error, print the info.
    }
  }
}
