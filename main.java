import mastermind_gui.*;

public class main {
	public static void main(String[] args) {
		final gui g = new gui();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				g.showGUI();
      }
    });
	}
}
