package mastermind_save_load;
import java.io.*;

public class save {
  File filename;

  public save (File filen) {
    filename = filen;
  }

  public void savefile(Object mastermind_game) {
    try {
      FileOutputStream saveFile = new FileOutputStream(filename);
      ObjectOutputStream save = new ObjectOutputStream(saveFile);
      save.writeObject(mastermind_game);
      save.close();
    } catch(Exception exc){
      exc.printStackTrace(); // If there was an error, print the info.
    }
  }
}
