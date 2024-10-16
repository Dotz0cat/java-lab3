import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Set;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    private Set<IrisData> dataSet;
    private JComboBox<String> varities;

    private JLabel meanLabel;
    private JLabel stdevLabel;
    private JLabel countLabel;
    private JLabel zScoreLabel;

    StatsPanel(Set<IrisData> dataSet) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.dataSet = dataSet;

        this.varities = new JComboBox<String>(this.dataSet.stream()
                .map(IrisData::variety)
                .distinct()
                .toArray(String[]::new)
        );

        this.varities.addActionListener(actionEvent -> {
            String selectedVariety = (String) varities.getSelectedItem();

            if (!selectedVariety.equals("All")) {
                updateStats(IrisData::sepalLength);
            }
            else {
                updateStats(IrisData::sepalLength, selectedVariety);
            }
        });

        this.add(this.varities);

        this.meanLabel = new JLabel("Mean: 0.0");
        this.stdevLabel = new JLabel("Standard Deviation: 0.0");
        this.countLabel = new JLabel("Count: 0");
        this.zScoreLabel = new JLabel("Z Score: 0.0");

        this.add(meanLabel);
        this.add(stdevLabel);
        this.add(countLabel);

        updateStats(IrisData::sepalLength);
    }

    public void updateStats(ToDoubleFunction<IrisData> selector) {
        this.meanLabel.setText("Mean: " + String.format("%.3f", getMean(selector)));
        this.stdevLabel.setText("Standard Deviation: " + String.format("%.3f", getStdev(selector)));
        this.countLabel.setText("Count: " + getCount());
    }

    public void updateStats(ToDoubleFunction<IrisData> selector, String type) {
        this.meanLabel.setText("Mean: " + String.format("%.3f", getMean(selector, type)));
        this.stdevLabel.setText("Standard Deviation: " + String.format("%.3f", getStdev(selector, type)));
        this.countLabel.setText("Count: " + getCount(type));
    }

    public double getMean(ToDoubleFunction<IrisData> selector) {
        return this.dataSet.stream()
                .mapToDouble(selector)
                .average()
                .orElse(0.0);
    }

    public double getMean(ToDoubleFunction<IrisData> selector, String type) {
        return this.dataSet.stream()
                .filter(irisData -> irisData.variety().equals(type))
                .mapToDouble(selector)
                .average()
                .orElse(0.0);
    }

    public int getCount() {
        return this.dataSet.size();
    }

    public int getCount(String type) {
        return (int) this.dataSet.stream()
                .map(IrisData::variety)
                .filter((String variety) -> variety.equals(type))
                .count();
    }

    public double getStdev(ToDoubleFunction<IrisData> selector) {
        double mean = getMean(selector);

        double variance = this.dataSet.stream()
                .mapToDouble(selector)
                .map(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0);

        return Math.sqrt(variance);
    }

    public double getStdev(ToDoubleFunction<IrisData> selector, String type) {
        double mean = getMean(selector, type);

        double variance = this.dataSet.stream()
                .filter(irisData -> irisData.variety().equals(type))
                .mapToDouble(selector)
                .map(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0);

        return Math.sqrt(variance);
    }

    public double zScore(double value, ToDoubleFunction<IrisData> selector) {
        double stdev = getStdev(selector);
        double mean = getMean(selector);

        return (value - mean)/stdev;
    }

    public double zScore(double value, ToDoubleFunction<IrisData> selector, String type) {
        double stdev = getStdev(selector, type);
        double mean = getMean(selector, type);

        return (value - mean)/stdev;
    }
}
