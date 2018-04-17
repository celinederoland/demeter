package accountancy.view.views.entry;

import accountancy.view.components.PPanel;
import accountancy.view.components.PTitle;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;

public class EntriesInnerPanel extends PPanel {

    public EntriesInnerPanel() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        PTitle title = new PTitle("Entrées");
        title.setFixedSize(Dimensions.CENTER_WIDTH);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

}
