package mastermind_gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

class gameHelpDialog extends JDialog {

		public gameHelpDialog() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));


        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel name = new JLabel("<html>Hier die Anleitung für ne Anleitung</html>");

        name.setFont(new Font("Serif", Font.BOLD, 13));
        name.setAlignmentX(0.5f);
        add(name);

        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton close = new JButton("O.K.");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });

        close.setAlignmentX(0.5f);
        add(close);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("Anleitung");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 200);
     }
}
