package accountancy.view.views.actions;

import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class ActionsOuterPanel extends PPanel {

    public ActionsOuterPanel() {

        super(10, 2);

        this.setFixedSize(Dimensions.NORTH);

        this.setLayout(new BorderLayout());
        this.add(new ActionsInnerPanel(), BorderLayout.CENTER);
    }
}
