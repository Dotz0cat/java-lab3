import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class ChartPanel extends JPanel {
    private static final String CHART_TITLE = "Iris chart";

    private final Set<IrisData> dataSet;

    private final JFreeChart chart;

    private ActionListener actionListener;

    ChartPanel(Set<IrisData> dataSet) {
        super(new BorderLayout());
        this.dataSet = dataSet;

        this.chart = ChartFactory.createBarChart(
            CHART_TITLE,
            "Variety",
            "Feature Measurement",
            createDataSet(_ -> true, _ -> true),
                PlotOrientation.HORIZONTAL,
                true,
                true,
                true
        );

        this.add(new org.jfree.chart.ChartPanel(chart));

        BarRenderer br = new BarRenderer();
        br.setItemMargin(0.0);
        chart.getCategoryPlot().setRenderer(br);
    }

    private CategoryDataset createDataSet(Predicate<? super Map.Entry> featureFilter, Predicate<? super IrisData> dataFilter) {
        DefaultMultiValueCategoryDataset dataset = new DefaultMultiValueCategoryDataset();
        ArrayList<Map.Entry<String, ToDoubleFunction<IrisData>>> features = new ArrayList<>(IrisData.SELECTOR_MAP.entrySet());

        // Iterate through each feature
        features.stream()
                .filter(featureFilter)
                .forEach(entry -> {
                    Map<String, List<Double>> varietyValues = this.dataSet.stream()
                    .filter(dataFilter)
                    .collect(Collectors.groupingBy(
                            IrisData::variety, // Group by variety
                            Collectors.mapping(irisData -> entry.getValue().applyAsDouble(irisData), // Apply the ToDoubleFunction
                                    Collectors.toList()) // Collect to a List<Double>
                    ));

                // Add the values to the dataset
                varietyValues.forEach((variety, values) -> {
                    dataset.add(values, variety, entry.getKey());
                });
            });



        return dataset;
    }

    public void filterChart(Predicate<? super Map.Entry> featureFilter, Predicate<? super IrisData> dataFilter) {
        this.chart.getCategoryPlot().setDataset(createDataSet(featureFilter, dataFilter));
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
