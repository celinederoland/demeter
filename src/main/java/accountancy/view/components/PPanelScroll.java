package accountancy.view.components;

import accountancy.view.config.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class PPanelScroll extends PPanel {

    protected final BasicScrollBarUI ui;
    protected final JScrollPane      scrollPane;

    public PPanelScroll(Component content) {

        super();
        this.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        ui = new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {

                this.thumbColor = Colors.GRADIENT;
                this.thumbDarkShadowColor = Colors.BACKGROUND;
                this.thumbHighlightColor = Colors.BACKGROUND;
                this.thumbLightShadowColor = Colors.BACKGROUND;
                this.trackColor = Colors.BACKGROUND;
                this.trackHighlightColor = Colors.BACKGROUND;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {

                return createZeroButton();
            }

            private JButton createZeroButton() {

                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {

                return createZeroButton();
            }
        };
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
