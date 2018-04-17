package accountancy.view.graphs;

import accountancy.model.selection.AmountByDate;
import accountancy.model.selection.TwoAxesSelection;
import accountancy.view.components.ColorPicker;
import accountancy.view.components.PLoader;
import accountancy.view.components.PPanelHorizontalScroll;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ViewDoubleChart extends DoubleView {

    public ViewDoubleChart(TwoAxesSelection selections) {

        super(selections);
    }

    public void make() {

        (new Make()).start();
    }

    class Make extends Thread {

        public void run() {

            PLoader loader = new PLoader("Loading ...");

            SwingUtilities.invokeLater(() -> {

                LinkedHashMap<String, LinkedHashMap<String, ArrayList<AmountByDate>>> datas =
                    selections.amounts();

                try {

                    int                                            n         = Math.min(14, datas.keySet().size());
                    ArrayList<Integer>                             ms        = new ArrayList<>();
                    LinkedHashMap<String, ArrayList<AmountByDate>> flatDatas = new LinkedHashMap<>();
                    for (String name : datas.keySet()) {

                        int m = Math.min(5, datas.get(name).keySet().size());
                        ms.add(m);
                        for (String subName : datas.get(name).keySet()) {
                            flatDatas.put(name + " -> " + subName, datas.get(name).get(subName));
                        }
                    }

                    ArrayList<ArrayList<Color>> colors     = ColorPicker.colorPicker().getColors(n, ms);
                    ArrayList<Color>            flatColors = new ArrayList<>();
                    for (ArrayList<Color> listColors : colors) {
                        flatColors.addAll(listColors);
                    }

                    amountsPane = new TablePanel(flatDatas, flatColors);
                    container.add(new PPanelHorizontalScroll(amountsPane), BorderLayout.NORTH);

                    makeGraph(flatDatas, flatColors, selections);

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
