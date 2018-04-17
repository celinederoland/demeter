package accountancy.view.components;

import javax.swing.*;
import java.awt.*;

public class PPanelHorizontalScroll extends PPanelScroll {

    public PPanelHorizontalScroll(Component content) {

        super(content);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        scrollPane.getHorizontalScrollBar().setUI(ui);
    }
}
