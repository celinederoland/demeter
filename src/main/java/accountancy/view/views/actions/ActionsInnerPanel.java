package accountancy.view.views.actions;

import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.view.actions.CSVImport;
import accountancy.view.actions.Clean;
import accountancy.view.components.PButton;
import accountancy.view.components.PPanel;

import javax.swing.*;

public class ActionsInnerPanel extends PPanel {

    public ActionsInnerPanel(BaseRepository repository, CsvImportRepository csvImportRepository) {

        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        PButton clean = new PButton("clean");
        clean.addActionListener(e -> new Clean(repository));
        this.add(clean);

        PButton csvImport = new PButton("Import CSV");
        csvImport.addActionListener(e -> new CSVImport(repository, csvImportRepository));
        this.add(csvImport);
    }
}
