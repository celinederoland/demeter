package accountancy.view.components;


import accountancy.view.config.Colors;

import javax.swing.*;
import java.awt.*;

public class PButton extends JButton {

    private String name;

    public PButton(String str) {

        super(str);
        this.name = str;
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BACKGROUND),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    public void paintComponent(Graphics g) {

        Graphics2D    g2d = (Graphics2D) g;
        GradientPaint gp  = new GradientPaint(0, 0, Colors.TEXT, 0, this.getHeight(), Colors.GRADIENT, false);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Colors.BACKGROUND);
        FontMetrics metrics = g2d.getFontMetrics();
        int         x       = (this.getWidth() - metrics.stringWidth(this.name)) / 2;
        int         y       = ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(this.name, x, y);
    }
}
