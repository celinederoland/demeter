package accountancy.view.views.account;

import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class AccountsOuterPanel extends PPanel {

    public AccountsOuterPanel() {

        super(10, 10, 20, 10);

        this.setMinimumSize(Dimensions.WEST);

        this.setLayout(new BorderLayout());
        this.add(new AccountsInnerPanel(), BorderLayout.CENTER);
    }
}
