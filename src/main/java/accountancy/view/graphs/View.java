package accountancy.view.graphs;

import accountancy.model.selection.AmountByDate;
import accountancy.model.selection.AxialSelection;
import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class View extends JFrame {

    protected PPanel graphPane;
    protected PPanel container;
    protected PPanel amountsPane;


    public View() {

        this.setTitle("vue");
        this.setSize(Dimensions.VIEW_FRAME);
        this.setLocationRelativeTo(null);

        container = new PPanel();
        container.setLayout(new BorderLayout());

        this.setContentPane(container);

        this.setVisible(true);

        this.make();
    }

    abstract public void make();

    protected void makeGraph(
        LinkedHashMap<String,
            ArrayList<AmountByDate>> datas,
        ArrayList<Color> colors,
        AxialSelection selections
    ) {

        switch (selections.style()) {
            case LINE:
                graphPane = new LineChartPanel(datas, colors);
                break;
            case BAR:
                graphPane = new BarChartPanel(datas, colors);
                break;
            case STACK:
                graphPane = new StackedBarChartPanel(datas, colors);
                break;
            case PIE:
                graphPane = new PieChartPanel(datas, colors);
        }
    }
}
