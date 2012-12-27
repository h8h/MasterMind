/**
 * Save and loader Files
 */
package mastermind_save_load;
import java.io.*;

/**
 * Load MasterMind from file
 */
public class load {
  File filename;

  /**
   * Class construction
   *
   * @param file in which the game is stored
   */
  public load (File file) {
    filename = file;
  }

  /**
   * Load MasterMind game from file
   *
   * @return all important core objects
   * @see mastermind_core.core#core(Object[] o)
   * @see mastermind_core.core#makePKG()
   */
  public Object[] loadfile() throws IOException,ClassNotFoundException {
    try {
      FileInputStream loadFile = new FileInputStream(filename);
      ObjectInputStream load = new ObjectInputStream(loadFile);
      Object[] o = (Object[]) load.readObject();
      load.close();
      return o;
    } catch(Exception exc){
      throw exc; //Redirect Error to GUI
    }
  }
}
