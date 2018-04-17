package accountancy.view.components;

import accountancy.view.config.Colors;

import javax.swing.*;
import java.awt.*;

public class PPanel extends JPanel {

    public PPanel() {

        this(0, 0, Colors.BACKGROUND);
    }

    public PPanel(int paddingLeft, int paddingTop, Color borderColor) {

        this(paddingLeft, paddingTop, paddingLeft, paddingTop, borderColor);
    }

    public PPanel(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom, Color borderColor) {

        this.setBackground(Colors.BACKGROUND);
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor),
            BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight)
        ));
    }

    public PPanel(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {

        this(paddingLeft, paddingTop, paddingRight, paddingBottom, Colors.BACKGROUND);
    }

    public PPanel(Color borderColor) {

        this(0, 0, borderColor);
    }

    public PPanel(int paddingLeft, int paddingTop) {

        this(paddingLeft, paddingTop, Colors.BACKGROUND);
    }


}
