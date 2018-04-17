package accountancy.view.components;

import accountancy.view.config.Colors;

import java.awt.*;

public class PTitle extends PPanel {

    public PTitle(String title) {

        super(5, 5, Colors.BORDER);
        this.setLayout(new BorderLayout());
        PLabel labelTitle = new PLabel(title);
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        this.add(labelTitle, BorderLayout.CENTER);
    }
}
