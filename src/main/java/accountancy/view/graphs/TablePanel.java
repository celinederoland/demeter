package accountancy.view.graphs;

import accountancy.model.selection.AmountByDate;
import accountancy.view.components.PPanel;
import accountancy.view.components.VLabel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TablePanel extends PPanel {

    public TablePanel(LinkedHashMap<String, ArrayList<AmountByDate>> datas, ArrayList<Color> colors) {

        super(5, 5, 5, 15);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        PPanel header = new PPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
        header.add(new VLabel("Date", new Dimension(250, 30)));
        this.add(header);

        try {

            int i = 0;
            for (String row : datas.keySet()) {

                int range = i % colors.size();

                PPanel rowPanel = new PPanel();
                rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.LINE_AXIS));
                VLabel rowTitle = new VLabel(row, new Dimension(250, 30));
                Color  color    = colors.get(range);
                rowTitle.setForeground(color);
                rowPanel.add(rowTitle);

                for (AmountByDate amountByDate : datas.get(row)) {

                    if (i == 0) {
                        header.add(new VLabel((new SimpleDateFormat("MM/yyyy")).format(amountByDate.date())));
                    }
                    VLabel label = new VLabel(String.valueOf(amountByDate.amount()));
                    label.setForeground(color);
                    rowPanel.add(label);
                }
                this.add(rowPanel);
                i++;
            }
        } catch (Exception exception) {

            System.out.println(exception.getMessage());
        }
    }
}
