package accountancy.view.components;


import accountancy.view.config.Colors;

import javax.swing.*;
import java.awt.event.FocusEvent;

public class PTextField extends JTextField {

    public PTextField() {

        super();
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        this.setBackground(Colors.BACKGROUND);
        this.setForeground(Colors.TEXT);
        this.setCaretColor(Colors.TEXT);

        this.addFocusListener(new AbstractMultiListener() {

            @Override public void focusGained(FocusEvent e) {

                SwingUtilities.invokeLater(() -> select(0, getText().length()));
            }

        });
    }


}
