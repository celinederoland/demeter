package accountancy.view.graphs;

import accountancy.model.selection.AmountByDate;
import accountancy.view.components.PPanel;
import accountancy.view.config.Colors;
import accountancy.view.config.Dimensions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BarChartPanel extends PPanel {

    public BarChartPanel(LinkedHashMap<String, ArrayList<AmountByDate>> datas, ArrayList<Color> colors) {

        this.setMinimumSize(Dimensions.VIEW_FRAME_GRAPH);
        this.setPreferredSize(Dimensions.VIEW_FRAME_GRAPH);
        this.setLayout(new BorderLayout());
        JFreeChart lineChart = ChartFactory.createBarChart(
            "Graph",
            "Date", "Montant",
            createDataset(datas),
            PlotOrientation.VERTICAL,
            true, true, false
        );


        lineChart.setBackgroundPaint(Colors.BACKGROUND);
        lineChart.getTitle().setVisible(false);
        lineChart.getLegend().setBackgroundPaint(Colors.BACKGROUND);
        lineChart.getLegend().setItemPaint(Colors.TEXT);

        CategoryPlot plot = lineChart.getCategoryPlot();

        ChartPanel chartPanel = new ChartPanel(lineChart);

        plot.getDomainAxis().setAxisLinePaint(Colors.TEXT);
        plot.getDomainAxis().setLabelPaint(Colors.TEXT);
        plot.getDomainAxis().setTickLabelPaint(Colors.TEXT);
        plot.getDomainAxis().setTickMarkPaint(Colors.TEXT);
        plot.getDomainAxis().setAxisLineStroke(new BasicStroke(1));
        plot.getDomainAxis().setTickMarkStroke(new BasicStroke(2));
        plot.getRangeAxis().setAxisLinePaint(Colors.TEXT);
        plot.getRangeAxis().setLabelPaint(Colors.TEXT);
        plot.getRangeAxis().setTickLabelPaint(Colors.TEXT);
        plot.getRangeAxis().setTickMarkPaint(Colors.TEXT);
        plot.getRangeAxis().setAxisLineStroke(new BasicStroke(1));
        plot.getRangeAxis().setTickMarkStroke(new BasicStroke(2));
        plot.setBackgroundPaint(Colors.BACKGROUND);
        plot.setDomainGridlinePaint(Colors.BACKGROUND);
        plot.setRangeGridlinePaint(Colors.BACKGROUND);


        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
        ((BarRenderer) plot.getRenderer()).setItemMargin(0);

        plot.getRenderer().setDefaultItemLabelsVisible(true);
        plot.setOutlineVisible(false);

        for (int i = 0 ; i < colors.size() ; i++) {
            plot.getRenderer().setSeriesPaint(i, colors.get(i % colors.size()));
            plot.getRenderer().setSeriesFillPaint(i, colors.get(i % colors.size()));
            plot.getRenderer().setSeriesStroke(i, new BasicStroke(3));
        }

        this.add(chartPanel, BorderLayout.CENTER);
    }

    private DefaultCategoryDataset createDataset(LinkedHashMap<String, ArrayList<AmountByDate>> datas) {

        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (String row : datas.keySet()) {

            for (AmountByDate amountByDate : datas.get(row)) {
                dataSet.addValue(
                    amountByDate.amount(),
                    row,
                    (new SimpleDateFormat("MM/yy")).format(amountByDate.date())
                );
            }
        }

        return dataSet;
    }
}
