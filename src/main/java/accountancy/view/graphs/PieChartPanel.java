package accountancy.view.graphs;

import accountancy.model.selection.AmountByDate;
import accountancy.view.components.PPanel;
import accountancy.view.config.Colors;
import accountancy.view.config.Dimensions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PieChartPanel extends PPanel {

    public PieChartPanel(LinkedHashMap<String, ArrayList<AmountByDate>> datas, ArrayList<Color> colors) {

        this.setMinimumSize(Dimensions.VIEW_FRAME_GRAPH);
        this.setPreferredSize(Dimensions.VIEW_FRAME_GRAPH);
        this.setLayout(new BorderLayout());
        JFreeChart lineChart = ChartFactory.createPieChart(
            "Graph",
            createDataset(datas),
            true, true, false
        );


        lineChart.setBackgroundPaint(Colors.BACKGROUND);
        lineChart.getTitle().setVisible(false);
        lineChart.getLegend().setBackgroundPaint(Colors.BACKGROUND);
        lineChart.getLegend().setItemPaint(Colors.TEXT);

        PiePlot    plot       = (PiePlot) lineChart.getPlot();
        ChartPanel chartPanel = new ChartPanel(lineChart);


        plot.setBackgroundPaint(Colors.BACKGROUND);


        plot.setOutlineVisible(false);

        int i = 0;
        for (String row : datas.keySet()) {
            plot.setSectionPaint(row, colors.get(i % colors.size()));
            i++;
        }

        this.add(chartPanel, BorderLayout.CENTER);
    }

    private PieDataset createDataset(LinkedHashMap<String, ArrayList<AmountByDate>> datas) {

        DefaultPieDataset dataSet = new DefaultPieDataset();

        for (String row : datas.keySet()) {

            double amount = 0;
            for (AmountByDate amountByDate : datas.get(row)) {
                amount += amountByDate.amount();
            }
            dataSet.setValue(row, amount);
        }

        return dataSet;
    }
}
