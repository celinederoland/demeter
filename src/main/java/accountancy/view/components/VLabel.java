package accountancy.view.components;


import accountancy.view.config.Colors;

import javax.swing.*;
import java.awt.*;

public class VLabel extends PPanel {

    private PLabel label = new PLabel("");

    public VLabel(String name) {

        this(name, new Dimension(100, 30));
    }

    public VLabel(String name, Dimension size) {

        super();
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);

        this.setLayout(new BorderLayout());
        label.setText(name);
        this.add(label, BorderLayout.CENTER);
    }

    public void setForeground(Color color) {

        super.setForeground(color);
        if (label != null) label.setForeground(color);
    }
}
