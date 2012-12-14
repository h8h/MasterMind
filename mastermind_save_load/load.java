package mastermind_save_load;
import java.io.*;
import mastermind_core.core;

class load {
  String filename;

  public load (String file) {
    filename = file;
  }

  private core loadFile() {
    try {
      FileInputStream loadFile = new FileInputStream(filename);
      ObjectInputStream load = new ObjectInputStream(loadFile);
      String name = (String) load.readObject();
      String name01 = (String) load.readObject();
      load.close();
      System.out.println(name);
      System.out.println(name01);
    } catch(Exception exc){
      exc.printStackTrace(); // If there was an error, print the info.
    }
    return null;
  }
}
