package accountancy.view.views.actions;

import accountancy.model.selection.AxialSelection;
import accountancy.model.selection.OneAxeSelection;
import accountancy.model.selection.TwoAxesSelection;
import accountancy.repository.AxialSelectionFactory;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.view.actions.CSVImport;
import accountancy.view.actions.Clean;
import accountancy.view.components.PButton;
import accountancy.view.components.PPanel;
import accountancy.view.graphs.ViewDoubleChart;
import accountancy.view.graphs.ViewLineChart;
import accountancy.view.graphs.ViewTransactions;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

public class ActionsInnerPanel extends PPanel {

    public ActionsInnerPanel(
        BaseRepository repository, CsvImportRepository csvImportRepository, AxialSelectionFactory selectionFactory
    ) {

        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        PButton clean = new PButton("clean");
        clean.addActionListener(e -> new Clean(repository));
        this.add(clean);

        PButton csvImport = new PButton("Import CSV");
        csvImport.addActionListener(e -> new CSVImport(repository, csvImportRepository));
        this.add(csvImport);

        LinkedHashMap<String, AxialSelection> selections = selectionFactory.selections();

        for (String name : selections.keySet()) {
            if (selections.get(name) instanceof OneAxeSelection) {
                OneAxeSelection selection = (OneAxeSelection) selections.get(name);

                PButton earns = new PButton(name);
                earns.addActionListener(e -> new ViewLineChart(selection));
                earns.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {

                        if (e.getButton() != MouseEvent.BUTTON1) {
                            new ViewTransactions(selection, repository);
                        }
                    }
                });
                this.add(earns);
            }
            else {
                TwoAxesSelection selection = (TwoAxesSelection) selections.get(name);

                PButton earns = new PButton(name);
                earns.addActionListener(e -> new ViewDoubleChart(selection));
                this.add(earns);
            }
        }
    }
}
