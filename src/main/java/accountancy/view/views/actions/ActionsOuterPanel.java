package accountancy.view.views.actions;

import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class ActionsOuterPanel extends PPanel {

    public ActionsOuterPanel(BaseRepository repository, CsvImportRepository csvImportRepository) {

        super(10, 2);

        this.setFixedSize(Dimensions.NORTH);

        this.setLayout(new BorderLayout());
        this.add(new ActionsInnerPanel(repository, csvImportRepository), BorderLayout.CENTER);
    }
}
