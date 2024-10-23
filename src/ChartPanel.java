import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

public class ChartPanel extends JPanel {
    private static final String CHART_TITLE = "Iris chart";

    private final Set<IrisData> dataSet;

    private JFreeChart chart;

    private ActionListener actionListener;

    ChartPanel(Set<IrisData> dataSet) {
        super();
        this.dataSet = dataSet;

        this.chart = ChartFactory.createBarChart(
            CHART_TITLE,
            "Something1",
            "Something2",
            createDataSet()
        );

        this.add(new org.jfree.chart.ChartPanel(chart));

        BarRenderer br = new BarRenderer();
        br.setItemMargin(0.0);
        chart.getCategoryPlot().setRenderer(br);
    }

    private CategoryDataset createDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<Map.Entry<String, ToDoubleFunction<IrisData>>> features = new ArrayList<>(IrisData.SELECTOR_MAP.entrySet());

        for (IrisData irisData : dataSet) {
            for (Map.Entry<String, ToDoubleFunction<IrisData>> feature : features) {
                String featureName = feature.getKey();
                double value = feature.getValue().applyAsDouble(irisData);
                dataset.addValue(value, irisData.variety(), featureName);
            }
        }

        return dataset;
    }

    public void addActionListener(ActionListener l) {
        this.actionListener = l;
    }

    private void dispatchAction() {
        if (this.actionListener == null) return;
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Something");
        this.actionListener.actionPerformed(event);
    }
}
