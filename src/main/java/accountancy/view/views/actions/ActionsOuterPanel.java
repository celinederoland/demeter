package accountancy.view.views.actions;

import accountancy.repository.AxialSelectionFactory;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class ActionsOuterPanel extends PPanel {

    public ActionsOuterPanel(
        BaseRepository repository, CsvImportRepository csvImportRepository, AxialSelectionFactory selectionFactory
    ) {

        super(10, 2);

        this.setFixedSize(Dimensions.NORTH);

        this.setLayout(new BorderLayout());
        this.add(new ActionsInnerPanel(repository, csvImportRepository, selectionFactory), BorderLayout.CENTER);
    }
}
