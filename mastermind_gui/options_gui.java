package mastermind_gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Options gui, change code size, color range and number of tries
 */
@SuppressWarnings("serial")
class options_gui extends JPanel {
    private int ColorRange = 8;
    private int codeLength = 4;
    private JSlider slide_colors;
    private JSlider slide_code;
    private JSpinner js_tries;
    private gui g;

    /**
     * Class construction
     *
     * @param g back reference to gui, needed to call for example new game
     */
    public options_gui(gui g) {
    	  this.g = g;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        Box vbox = Box.createVerticalBox();
        setBorder(BorderFactory.createTitledBorder("::Einstellungen::"));
        slide_colors = new JSlider(JSlider.VERTICAL,2,14,8);
        slide_code  = new JSlider(JSlider.VERTICAL,2,10,4);
        js_tries = new JSpinner();
        js_tries.setMaximumSize(new Dimension(80,50));
        ((JSpinner.DefaultEditor)js_tries.getEditor()).getTextField().setColumns(2);
        setNumberOfTries((int)((3*codeLength)/2));
        js_tries.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            JSpinner source = (JSpinner) e.getSource();
            if ((int)source.getValue() > 99) {
              source.setValue(99);
            }
            if ((int)source.getValue() < 0) {
              source.setValue(0);
            }
          }
        });
        slide_colors.setMajorTickSpacing(1);
        slide_colors.setPaintLabels(true);
        slide_colors.setPaintTicks(true);
        slide_colors.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    setColorRange(source.getValue());
          }
        });

        slide_code.setMajorTickSpacing(1);
        slide_code.setPaintLabels(true);
        slide_code.setPaintTicks(true);
        slide_code.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            setCodeSize(source.getValue());
            setNumberOfTries((int)((3*source.getValue())/2));
          }
        });
        JButton ak = new JButton ("Neues Spiel");
        ak.setMnemonic(KeyEvent.VK_P);
        ak.setAlignmentX(Component.RIGHT_ALIGNMENT);
        ak.addActionListener(new ActionListener () {
          public void actionPerformed (ActionEvent e) {
            newGame();
          }
        });
        //Margin Top <-> Label for Color / Code Length Labels
        vbox.add(Box.createVerticalStrut(10));
        Box hlabl = Box.createHorizontalBox();
        hlabl.add(new JLabel("<html>Anzahl<br>Farben:</html>"));
        hlabl.add(Box.createHorizontalGlue());
        hlabl.add(new JLabel("<html>Code<br>Länge:</html>"));
        vbox.add(hlabl);
        //Margin Label <-> JSpinner
        vbox.add(Box.createVerticalStrut(15));
        Box hbox = Box.createHorizontalBox();
        hbox.add(slide_colors);
        hbox.add(Box.createHorizontalGlue());
        hbox.add(slide_code);
        hbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox);
        //Margin JSpinner <-> Tries
        vbox.add(Box.createVerticalStrut(20));
        Box trieslbl = Box.createHorizontalBox();
        trieslbl.add(new JLabel("Versuche:  "));
        trieslbl.add(js_tries);
        trieslbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
        vbox.add(trieslbl);
        //Margin Tries <-> New Game Button
        vbox.add(Box.createVerticalStrut(20));
        vbox.add(ak);
        add(vbox);
    }


    /**
     * Create new game
     *
     * @see gui#newGame()
     */
    private void newGame() {
      g.newGame();
    }

    /**
     * Return enabled colors size
     *
     * @return value which the user has selected
     */
    protected int getColorRange() {
      return ColorRange;
    }

    /**
     * Change value of enabled colors size
     *
     * @param cr set available color range
     * @see mastermind_core.core#generateColors(int)
     */
    protected void setColorRange(int cr) {
      ColorRange = cr;
      slide_colors.setValue(ColorRange);
    }

    /**
     * Get secret code size
     *
     * @return value which the user has selected
     */
    protected int getCodeSize(){
      return codeLength;
    }

    /**
     * Change value of secret code size
     *
     * @param cl code size
     * @see mastermind_core.core#generateCode()
     */
    protected void setCodeSize(int cl) {
      codeLength = cl;
      slide_code.setValue(codeLength);
    }

    /**
     * Return number of tries, the user can do, before he lose the game
     *
     * @return value which the user has selected
     */
    protected int getNumberOfTries() {
    	return (int) js_tries.getValue();
    }

    /**
     * Change number of tries
     *
     * @param tries number of tries, user can do, before he lose the game
     * @see mastermind_core.core#checkTries()
     */
    protected void setNumberOfTries(int tries) {
    	js_tries.setValue(tries);
    }
}
