package accountancy.view.graphs;

import accountancy.model.base.Transaction;
import accountancy.model.selection.AmountByDate;
import accountancy.model.selection.OneAxeSelection;
import accountancy.repository.BaseRepository;
import accountancy.view.components.*;
import accountancy.view.views.entry.TransactionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ViewTransactions extends SimpleView {

    private final BaseRepository repository;

    public ViewTransactions(OneAxeSelection selections, BaseRepository repository) {

        super(selections);
        this.repository = repository;
    }

    public void make() {

        (new Make()).start();
    }

    class Make extends Thread {

        public void run() {

            PLoader                                        loader  = new PLoader("Loading ...");
            LinkedHashMap<String, ArrayList<Transaction>>  entries = selections.transactions();
            LinkedHashMap<String, ArrayList<AmountByDate>> datas   = selections.amounts();

            SwingUtilities.invokeLater(() -> {

                try {
                    ArrayList<Color> colors = ColorPicker.colorPicker().getColors(
                        Math.min(14, datas.keySet().size())
                    );


                    graphPane = new PPanel(5, 5, 15, 5);
                    graphPane.setLayout(new BoxLayout(graphPane, BoxLayout.PAGE_AXIS));

                    int i = 0;
                    for (String name : entries.keySet()) {
                        for (Transaction transaction : entries.get(name)) {
                            PPanel panelEntry = new TransactionPanel(transaction, repository);
                            panelEntry.setForeground(colors.get(i % colors.size()));
                            graphPane.add(panelEntry);
                        }
                        i++;
                    }
                    container.add(new PPanelVerticalScroll(graphPane), BorderLayout.CENTER);


                    amountsPane = new TablePanel(datas, colors);
                    container.add(new PPanelHorizontalScroll(amountsPane), BorderLayout.NORTH);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                revalidate();
                repaint();
                loader.dispose();
            });

        }
    }
}
