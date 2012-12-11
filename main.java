import mastermind_gui.*;
import javax.swing.*;

public class main {
	public static void main(String[] args) {
    final gui g = new gui();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

        try {
          UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" );
        }
        catch ( Exception e ) {
          e.printStackTrace();
        }

        g.showGUI();
      }
    });
	}
}
