import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class DataPanel extends JPanel {
    private TablePanel table;
    private StatsPanel stats;
    private ChartPanel chart;
    private DetailsPanel details;
    private FileHandler fileHandler;
    private Set<IrisData> dataSet;

    DataPanel(String file) {
        super();

        this.setLayout(new GridBagLayout());

        this.fileHandler = new FileHandler(file);
        this.dataSet = this.fileHandler.getSetOf();

        this.table = new TablePanel(this.dataSet.stream());
        this.stats = new StatsPanel(this.dataSet.stream());
        this.chart = new ChartPanel(this.dataSet.stream());
        this.details = new DetailsPanel(this.dataSet.stream());

        GridBagConstraints tableConstraints = new GridBagConstraints();
        GridBagConstraints statsConstraints = new GridBagConstraints();
        GridBagConstraints chartConstraints = new GridBagConstraints();
        GridBagConstraints detailsConstraints = new GridBagConstraints();

        tableConstraints.anchor = GridBagConstraints.PAGE_START;
        tableConstraints.fill = GridBagConstraints.HORIZONTAL;
        tableConstraints.weightx = 1.0;
        tableConstraints.gridy = 0;
        tableConstraints.gridx = 0;

        chartConstraints.anchor = GridBagConstraints.PAGE_START;
        chartConstraints.fill = GridBagConstraints.HORIZONTAL;
        chartConstraints.weightx = 1.0;
        chartConstraints.gridy = 1;
        chartConstraints.gridx = 0;

        detailsConstraints.anchor = GridBagConstraints.PAGE_START;
        detailsConstraints.fill = GridBagConstraints.HORIZONTAL;
        detailsConstraints.weightx = 1.0;
        detailsConstraints.gridy = 0;
        detailsConstraints.gridx = 2;

        statsConstraints.anchor = GridBagConstraints.PAGE_START;
        statsConstraints.fill = GridBagConstraints.HORIZONTAL;
        statsConstraints.weightx = 1.0;
        statsConstraints.gridy = 1;
        statsConstraints.gridx = 2;

        this.add(this.table, tableConstraints);

        this.add(this.chart, chartConstraints);

        this.add(this.details, detailsConstraints);

        this.add(this.stats, statsConstraints);
    }
}
