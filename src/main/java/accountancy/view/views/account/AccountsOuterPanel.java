package accountancy.view.views.account;

import accountancy.repository.BaseRepository;
import accountancy.repository.SelectionProvider;
import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class AccountsOuterPanel extends PPanel {

    public AccountsOuterPanel(BaseRepository repository, SelectionProvider selectionProvider) {

        super(10, 10, 20, 10);

        this.setMinimumSize(Dimensions.WEST);

        this.setLayout(new BorderLayout());
        this.add(new AccountsInnerPanel(repository, selectionProvider), BorderLayout.CENTER);
    }
}
