package accountancy.view.views.transaction;

import accountancy.repository.BaseRepository;
import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class TransactionsOuterPanel extends PPanel {

    public TransactionsOuterPanel(BaseRepository repository) {

        super(10, 10);
        this.setMinimumSize(Dimensions.CENTER);

        this.setLayout(new BorderLayout());
        this.add(new TransactionsInnerPanel(repository), BorderLayout.CENTER);
    }
}
