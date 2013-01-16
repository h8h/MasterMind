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
class gameOptions extends JPanel {
    private int colorRange = 6;
    private int codeLength = 4;
    protected boolean manualCode;
    private JSlider slideColors;
    private JSlider slideCode;
    private JSpinner jsTries;
    private JCheckBox manuCode;
    private gui g;

    /**
     * Class construction
     *
     * @param g back reference to gui, needed to call for example new game
     */
    public gameOptions(gui g) {
    	  this.g = g;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        Box vbox = Box.createVerticalBox();
        setBorder(BorderFactory.createTitledBorder("::Einstellungen::"));
        slideColors = new JSlider(JSlider.VERTICAL,2,14,colorRange);
        slideCode  = new JSlider(JSlider.VERTICAL,2,10,codeLength);
        jsTries = new JSpinner();
        jsTries.setMaximumSize(new Dimension(80,50));
        ((JSpinner.DefaultEditor)jsTries.getEditor()).getTextField().setColumns(2);
        setNumberOfTries((int)((3*codeLength)/2));
        jsTries.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            JSpinner source = (JSpinner) e.getSource();
            if ((Integer)source.getValue() > 99) {
              source.setValue(99);
            }
            if ((Integer)source.getValue() < 0) {
              source.setValue(0);
            }
          }
        });
        slideColors.setMajorTickSpacing(1);
        slideColors.setPaintLabels(true);
        slideColors.setPaintTicks(true);
        slideColors.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    setColorRange(source.getValue());
          }
        });

        slideCode.setMajorTickSpacing(1);
        slideCode.setPaintLabels(true);
        slideCode.setPaintTicks(true);
        slideCode.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            setCodeSize(source.getValue());
            setNumberOfTries((Integer)((3*source.getValue())/2));
          }
        });
        manuCode = new JCheckBox("Code manuell setzen");
        manuCode.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton ak = new JButton ("<html><u>N</u>eues Spiel</html>");
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
        hbox.add(slideColors);
        hbox.add(Box.createHorizontalGlue());
        hbox.add(slideCode);
        hbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox);
        //Margin JSpinner <-> Tries
        vbox.add(Box.createVerticalStrut(20));
        Box trieslbl = Box.createHorizontalBox();
        trieslbl.add(new JLabel("Versuche:  "));
        trieslbl.add(jsTries);
        trieslbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
        vbox.add(trieslbl);
        //Margin Tries <-> New Game Button
        vbox.add(Box.createVerticalStrut(20));
        vbox.add(manuCode);
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
      return colorRange;
    }

    /**
     * Change value of enabled colors size
     *
     * @param cr set available color range
     * @see mastermind_core.core#generateColors(int)
     */
    protected void setColorRange(int cr) {
      colorRange = cr;
      slideColors.setValue(colorRange);
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
      slideCode.setValue(codeLength);
    }

    /**
     * Return number of tries, the user can do, before he lose the game
     *
     * @return value which the user has selected
     */
    protected int getNumberOfTries() {
    	return (int) jsTries.getValue();
    }

    /**
     * Change number of tries
     *
     * @param tries number of tries, user can do, before he lose the game
     * @see mastermind_core.core#checkTries()
     */
    protected void setNumberOfTries(int tries) {
    	jsTries.setValue(tries);
    }

    /**
     * Update manualCode
     *
     * @see gameArea#removeManualCode()
     */
    protected void initManualCode() {
        manualCode = manuCode.isSelected();
    }
}
