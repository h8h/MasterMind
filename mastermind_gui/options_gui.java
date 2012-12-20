package mastermind_gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;

public class options_gui extends JPanel {
    private int ColorRange = 8;
    private int codeLength = 4;
    private JSlider slide_colors;
    private JSlider slide_code;
    private JSpinner js_tries;
    private gui g;

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
        setTriesLength((int)((3*codeLength)/2));
        js_tries.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            JSpinner source = (JSpinner) e.getSource();
            if ((int)source.getValue() > 99) {
              source.setValue(99);
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
            setcodeLength(source.getValue());
            setTriesLength((int)((3*source.getValue())/2));
          }
        });
        JButton ak = new JButton ("Neues Spiel");
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
        hlabl.add(new JLabel("<html>Geheimer<br>Code:</html>"));
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


    private void newGame() {
      g.newGame();
    }

    public int getColorRange() {
      return ColorRange;
    }

    protected void setColorRange(int cr) {
      ColorRange = cr;
      slide_colors.setValue(ColorRange);
    }

    public int getcodeLength(){
      return codeLength;
    }

    protected void setcodeLength(int cl) {
      codeLength = cl;
      slide_code.setValue(codeLength);
    }

    public int gettriesLength() {
    	return (int) js_tries.getValue();
    }

    protected void setTriesLength(int tries) {
    	js_tries.setValue(tries);
    }

}

