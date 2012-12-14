package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class options_gui extends JPanel {
    private int ColorRange = 8;
    private int codeLength = 4;
    private JSlider slide_colors;
    private JSlider slide_code;

    public options_gui() {
        slide_colors = new JSlider(JSlider.VERTICAL,2,14,8);
        slide_code  = new JSlider(JSlider.VERTICAL,2,10,4);

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
          }
        });

        add(slide_colors);
        add(slide_code);
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
}

