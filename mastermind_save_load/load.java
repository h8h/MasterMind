package mastermind_save_load;
import java.io.*;

public class load {
  File filename;

  public load (File file) {
    filename = file;
  }

  public Object[] loadfile() {
    try {
      FileInputStream loadFile = new FileInputStream(filename);
      ObjectInputStream load = new ObjectInputStream(loadFile);
      Object[] o = (Object[]) load.readObject();
      load.close();
      return o;
    } catch(Exception exc){
      exc.printStackTrace(); // If there was an error, print the info.
    }
    return null;
  }
}
