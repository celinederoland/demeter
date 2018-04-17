package accountancy.view.views.account;

import accountancy.view.components.PPanel;
import accountancy.view.components.PTitle;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;

public class AccountsInnerPanel extends PPanel {

    public AccountsInnerPanel() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        PTitle title = new PTitle("Comptes");
        title.setFixedSize(Dimensions.WEST_WIDTH);
        add(title);

        add(Box.createRigidArea(new Dimension(0, 10)));
    }

}
