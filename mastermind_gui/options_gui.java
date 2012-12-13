package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class options_gui extends JPanel {
    private int ColorRange = 8;
    private int codeLength = 4;

    public options_gui() {
        JSlider slide_colors = new JSlider(JSlider.VERTICAL,2,16,8);
        JSlider slide_code  = new JSlider(JSlider.VERTICAL,2,10,4);

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

    private void setColorRange(int cr) {
      ColorRange = cr;
    }

    public int getcodeLength(){
      return codeLength;
    }

    private void setcodeLength(int cl) {
      codeLength = cl;
    }
}

