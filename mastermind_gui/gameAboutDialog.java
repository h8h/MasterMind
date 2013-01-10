package mastermind_gui;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Desktop;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
/**
 * Shows about dialog, to get link to github and to know who developed this nice programm
 */
class gameAboutDialog extends JDialog {

        /**
         * Class construction<br>
         * Creates JLabel with text
         */
        public gameAboutDialog() {
          setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

          JLabel name = new JLabel("<html><br><font size=+2>Info</font><br><br>MasterMind PP-1 wurde im Rahmen eines Hochschulprojektes entwickelt, in der Hoffnung es ist intuitiv und hilfreich, um die grauen Zellen zu reaktivieren.<br><br><b><u>Wichtig: </u>Für evtl. geschädigte oder zerstörte Gehirnzellen, beim Versuch den Code zu lösen tragen weder die HTW-Aalen, noch die Entwickler Haftung.</b><br><br>Beim mehrmaligen KLICK auf die OK oder n-Taste, kann der Code ohne große Bemühungen automatisch gelöst werden (durch den Computer).<br><br><font size =+2>Entwickler und Projekt</font><br><br>Entwickler:<br>Christian Homeyer (H8H)<br>Sebastian Orlowski<br>Stefan Schmid<br>Timo Bonzheim<br><br>MasterMind PP-1 wurde unter GNU General Public License veröffentlich,<br>dieses Projekt finden Sie unter GitHub www.github.com/h8h/MasterMind</html>");
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

          setTitle("Programminfo");
          setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          setLocationRelativeTo(null);
          setSize(580,400);
       }
}
