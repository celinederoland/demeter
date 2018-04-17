package accountancy.view.components;

import javax.swing.*;
import java.awt.*;

public class PPanelVerticalScroll extends PPanelScroll {

    public PPanelVerticalScroll(Component content) {

        super(content);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getVerticalScrollBar().setUI(ui);

    }
}
