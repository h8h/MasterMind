package mastermind_gui.mastermind_templates;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;


public class pin extends JButton implements MouseListener {
    
		public pin() {
        this.addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.getBackground());
        g2.fillRect(0,0,this.getWidth(),this.getHeight());

        float scale = (50f/30f)*this.getFont().getSize2D();

        drawLiquidButton(this.getForeground(),
            this.getWidth(), this.getHeight(),
            getText(), scale,
            g2);
    }

    protected void drawLiquidButton(Color base,
                int width, int height,
                String text, float scale,
                Graphics2D g2) {

        // calculate inset
        int inset = (int)(scale*0.04f);
        int w = width - inset*2 - 1;
        int h = height - (int)(scale*0.1f) - 1;
        g2.translate(inset,0);
				g2.setColor(base );
        if(pressed) {
						g2.setColor( base );
            g2.translate(0, 0.04f*scale);
        }
				g2.fillOval(0, 0, w, h );
				g2.drawOval(0,0,w,h);
        g2.translate(-inset,0);
    }

  protected void paintBorder(Graphics g) {

  }

    // generate the alpha version of this color
    protected static Color alphaColor(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(),
            color.getBlue(), alpha);
    }


    /* mouse listener implementation */
    protected boolean pressed = false;
    public void mouseExited(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) {
        pressed = false;
    }
    public void mousePressed(MouseEvent evt) {
        pressed = true;
    }



    public static void p(String s) {
        System.out.println(s);
    }
}
