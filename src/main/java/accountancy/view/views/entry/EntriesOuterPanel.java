package accountancy.view.views.entry;

import accountancy.repository.BaseRepository;
import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class EntriesOuterPanel extends PPanel {

    public EntriesOuterPanel(BaseRepository repository) {

        super(10, 10);
        this.setMinimumSize(Dimensions.CENTER);

        this.setLayout(new BorderLayout());
        this.add(new EntriesInnerPanel(repository), BorderLayout.CENTER);
    }
}
