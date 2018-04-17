package accountancy.view.components;


import accountancy.view.config.Colors;

import javax.swing.*;
import java.awt.*;

public class PComboButton extends JButton {

    private final String name;

    public PComboButton() {

        super("⏷");
        this.name = "⏷";
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BACKGROUND),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Colors.BACKGROUND);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2d.setColor(Colors.TEXT);
        FontMetrics metrics = g2d.getFontMetrics();
        int         x       = (this.getWidth() - metrics.stringWidth(this.name)) / 2;
        int         y       = ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.setColor(Colors.BORDER);
        g2d.drawString(this.name, x, y);
    }
}
