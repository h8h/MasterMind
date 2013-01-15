package mastermind_gui;

import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
/**
 * Shows dialog with given text and title
 */
@SuppressWarnings("serial")
class gameDialog extends JDialog {

        /**
         * Class construction
         *
         * @param title gameDialog title
         * @param text text to show
         */
        public gameDialog(String title, String text) {
          setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

          JLabel name = new JLabel(text);
          add(name);

          add(Box.createRigidArea(new Dimension(0, 10)));

          JButton close = new JButton("O.K.");
          close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
          });
          close.setAlignmentX(0.15f);

          add(close);

          setModalityType(ModalityType.APPLICATION_MODAL);

          setTitle(title);
          setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          setLocationRelativeTo(null);
          setSize(580,480);
       }
}
