package accountancy.view.graphs;

import accountancy.model.selection.AmountByDate;
import accountancy.model.selection.OneAxeSelection;
import accountancy.view.components.ColorPicker;
import accountancy.view.components.PLoader;
import accountancy.view.components.PPanelHorizontalScroll;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ViewLineChart extends SimpleView {

    public ViewLineChart(OneAxeSelection selections) {

        super(selections);
    }

    public void make() {

        (new Make()).start();
    }

    class Make extends Thread {

        public void run() {

            PLoader loader = new PLoader("Loading ...");

            SwingUtilities.invokeLater(() -> {

                LinkedHashMap<String, ArrayList<AmountByDate>> datas = selections.amounts();

                try {
                    ArrayList<Color> colors = ColorPicker.colorPicker().getColors(Math.max(14, datas.keySet().size()));

                    amountsPane = new TablePanel(datas, colors);
                    container.add(new PPanelHorizontalScroll(amountsPane), BorderLayout.NORTH);

                    makeGraph(datas, colors, selections);
                    container.add(graphPane, BorderLayout.CENTER);
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
