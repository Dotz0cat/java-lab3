import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.function.ToDoubleFunction;

public class StatsPanel extends JPanel {
    private Set<IrisData> dataSet;
    private JComboBox<String> varities;

    private JPanel featuresPanel;
    private Map<String, FeatureLabels> featureLabelsMap;

    private ActionListener actionListener;

    StatsPanel(Set<IrisData> dataSet) {
        super(new BorderLayout());

        this.dataSet = dataSet;

        this.featuresPanel = new JPanel();

        this.featureLabelsMap = new WeakHashMap<String, FeatureLabels>();

        this.varities = new JComboBox<String>();
        this.varities.addItem("All");
        this.dataSet.stream()
                .map(IrisData::variety)
                .distinct()
                .forEach(this.varities::addItem);

        this.varities.addActionListener(actionEvent -> {
            updateAllStats();
            dispatchAction();
        });

        this.add(this.varities, BorderLayout.PAGE_START);
        this.add(this.featuresPanel, BorderLayout.CENTER);

        IrisData.SELECTOR_MAP.forEach(this::addToFeatureMap);
    }

    public void updateAllStats() {
        String selectedVariety = (String) varities.getSelectedItem();

        if (selectedVariety.equals("All")) {
            for (FeatureLabels featureLabel : featureLabelsMap.values()) {
                featureLabel.updateStats();
            }
        }
        else {
            for (FeatureLabels featureLabel : featureLabelsMap.values()) {
                featureLabel.updateStats(selectedVariety);
            }
        }
    }

    public void updateAllStats(IrisData irisData) {
        String selectedVariety = (String) varities.getSelectedItem();

        if (selectedVariety.equals("All")) {
            for (FeatureLabels featureLabel : featureLabelsMap.values()) {
                featureLabel.updateStats(irisData);
            }
        }
        else {
            for (FeatureLabels featureLabel : featureLabelsMap.values()) {
                featureLabel.updateStats(irisData, selectedVariety);
            }
        }
    }

    public void updateStatsForFeature(FeatureLabels featureLabel) {
        String selectedVariety = (String) varities.getSelectedItem();

        if (selectedVariety.equals("All")) {
            featureLabel.updateStats();
        }
        else {
            featureLabel.updateStats(selectedVariety);
        }
    }

    public String getSelectedVariety() {
        String variety = (String) this.varities.getSelectedItem();
        return variety.equals("All") ? null : variety;
    }

    private void addFeature(String feature) {
        addToFeatureMap(feature, IrisData.SELECTOR_MAP.get(feature));
    }

    public void useFeatures(String ... features) {
        featuresPanel.removeAll();
        featureLabelsMap.clear();

        Arrays.stream(features)
                .filter(IrisData.SELECTOR_MAP::containsKey)
                .forEach(this::addFeature);

        featuresPanel.repaint();
        featuresPanel.revalidate();
    }

    private void addToFeatureMap(String feature, ToDoubleFunction<IrisData> featureSelector) {
        this.featureLabelsMap.put(feature, new FeatureLabels(feature, featureSelector));
        this.featuresPanel.add(featureLabelsMap.get(feature));
        updateStatsForFeature(this.featureLabelsMap.get(feature));
    }

    private double getMean(ToDoubleFunction<IrisData> selector) {
        return this.dataSet.stream()
                .mapToDouble(selector)
                .average()
                .orElse(0.0);
    }

    private double getMean(ToDoubleFunction<IrisData> selector, String type) {
        return this.dataSet.stream()
                .filter(irisData -> irisData.variety().equals(type))
                .mapToDouble(selector)
                .average()
                .orElse(0.0);
    }

    private int getCount() {
        return this.dataSet.size();
    }

    private int getCount(String type) {
        return (int) this.dataSet.stream()
                .map(IrisData::variety)
                .filter((String variety) -> variety.equals(type))
                .count();
    }

    private double getStdev(ToDoubleFunction<IrisData> selector) {
        double mean = getMean(selector);

        double variance = this.dataSet.stream()
                .mapToDouble(selector)
                .map(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0);

        return Math.sqrt(variance);
    }

    private double getStdev(ToDoubleFunction<IrisData> selector, String type) {
        double mean = getMean(selector, type);

        double variance = this.dataSet.stream()
                .filter(irisData -> irisData.variety().equals(type))
                .mapToDouble(selector)
                .map(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0);

        return Math.sqrt(variance);
    }

    private double getZScore(IrisData irisData, ToDoubleFunction<IrisData> selector) {
        double stdev = getStdev(selector);
        double mean = getMean(selector);

        return (selector.applyAsDouble(irisData) - mean)/stdev;
    }

    private double getZScore(IrisData irisData, ToDoubleFunction<IrisData> selector, String type) {
        double stdev = getStdev(selector, type);
        double mean = getMean(selector, type);

        return (selector.applyAsDouble(irisData) - mean)/stdev;
    }

    private class FeatureLabels extends JPanel {
        private JLabel feature;
        private ToDoubleFunction<IrisData> selector;

        private JLabel meanLabel;
        private JLabel stdevLabel;
        private JLabel countLabel;
        private JLabel zScoreLabel;

        FeatureLabels(String feature, ToDoubleFunction<IrisData> selector) {
            super();
            this.setLayout(new GridLayout(0, 1));

            this.feature = new JLabel(feature, JLabel.CENTER);
            this.add(this.feature);

            this.selector = selector;

            this.meanLabel = new JLabel("Mean: 0.0", JLabel.CENTER);
            this.stdevLabel = new JLabel("Standard Deviation: 0.0", JLabel.CENTER);
            this.countLabel = new JLabel("Count: 0", JLabel.CENTER);
            this.zScoreLabel = new JLabel("Z Score: 0.0", JLabel.CENTER);

            this.add(meanLabel);
            this.add(stdevLabel);
            this.add(countLabel);
            this.add(zScoreLabel);

            this.zScoreLabel.setVisible(false);
        }

        public void updateStats() {
            this.meanLabel.setText("Mean: " + String.format("%.3f", getMean(this.selector)));
            this.stdevLabel.setText("Standard Deviation: " + String.format("%.3f", getStdev(this.selector)));
            this.countLabel.setText("Count: " + getCount());
            this.zScoreLabel.setVisible(false);
        }

        public void updateStats(String type) {
            this.meanLabel.setText("Mean: " + String.format("%.3f", getMean(this.selector, type)));
            this.stdevLabel.setText("Standard Deviation: " + String.format("%.3f", getStdev(this.selector, type)));
            this.countLabel.setText("Count: " + getCount(type));
            this.zScoreLabel.setVisible(false);
        }

        public void updateStats(IrisData irisData) {
            this.meanLabel.setText("Mean: " + String.format("%.3f", getMean(this.selector)));
            this.stdevLabel.setText("Standard Deviation: " + String.format("%.3f", getStdev(this.selector)));
            this.countLabel.setText("Count: " + getCount());
            this.zScoreLabel.setText("Z Score: " + String.format("%.3f", getZScore(irisData, this.selector)));
            this.zScoreLabel.setVisible(true);
        }

        public void updateStats(IrisData irisData, String type) {
            this.meanLabel.setText("Mean: " + String.format("%.3f", getMean(this.selector, type)));
            this.stdevLabel.setText("Standard Deviation: " + String.format("%.3f", getStdev(this.selector, type)));
            this.countLabel.setText("Count: " + getCount(type));
            this.zScoreLabel.setText("Z Score: " + String.format("%.3f", getZScore(irisData, this.selector, type)));
            this.zScoreLabel.setVisible(true);
        }
    }

    public void addActionListener(ActionListener l) {
        this.actionListener = l;
    }

    private void dispatchAction() {
        if (this.actionListener == null) return;

        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Selection Changed");

        this.actionListener.actionPerformed(event);
    }
}
