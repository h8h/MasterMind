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
 * Shows help dialog, to get starting with the mastermind game
 */
class gameHelpDialog extends JDialog {

        /**
         * Class construction<br>
         * Creates JLabel with help text for quick help. Otherwise RTFM - Read the fine manual
         *
         * <a href="http://de.wikipedia.org/wiki/Mastermind">Wikipedia: MasterMind</a>
         */
        public gameHelpDialog() {
          setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
          JLabel name = new JLabel("<html><br><font size =+2>Anleitung</font><br><br>Klicke auf die Farben, um sie in ein leeres Feld einzusetzen oder klicke auf die Felder um die Farbe zu wechseln.<br><br>Alternativ, klicke die Taste <b>a</b>, dann die Farbnummer (1-9/0 für 10 bzw. Q W E R), dann die Spalte(1 - 9 / 0 für 10).<br><br>Zum löschen klicke die Taste <b>d</b>, dann die Spaltennummer.<br><br>Alt+p startet ein neues Spiel.<br><br>Leere Felder (grau) füllt der Computer, nach klicken der OK oder n-Taste, automatisch aus.<br><br>Durch benutzen der Hilfefunktion, können unnötige Spielzüge verhindert werden, indem der Computer einen entsprechenden Hinweis gibt.<br><br> Ein <b>schwarzer Pin</b> bedeutet richtige Farbe und richtiger Platz.<br><br>Ein <b>roter Pin</b>, meist auch bekannt mit weißem Pin, bedeutet richtige Farbe und falscher Platz.</html>");
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

          setTitle("Anleitung");
          setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          setLocationRelativeTo(null);
          setSize(580,400);
       }
}
