package accountancy.view.components;


import accountancy.view.config.Colors;

import javax.swing.*;

public class PLabel extends JLabel {

    public PLabel(String name) {

        super(name);
        this.setForeground(Colors.TEXT);
    }
}
