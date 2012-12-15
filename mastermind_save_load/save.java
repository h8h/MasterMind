package mastermind_save_load;
import java.io.*;

public class save {
  File filename;

  public save (File filen) {
    filename = filen;
  }

  public void savefile(Object mastermind_game) throws IOException {
    try {
      FileOutputStream saveFile = new FileOutputStream(filename);
      ObjectOutputStream save = new ObjectOutputStream(saveFile);
      save.writeObject(mastermind_game);
      save.close();
    } catch(Exception exc){
     throw exc;//Redirect Error to GUI
    }
  }
}
