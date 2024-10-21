import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

        this.table = new TablePanel(this.dataSet);
        this.stats = new StatsPanel(this.dataSet);
        this.chart = new ChartPanel(this.dataSet.stream());
        this.details = new DetailsPanel();

        GridBagConstraints tableConstraints = new GridBagConstraints();
        GridBagConstraints statsConstraints = new GridBagConstraints();
        GridBagConstraints chartConstraints = new GridBagConstraints();
        GridBagConstraints detailsConstraints = new GridBagConstraints();

        tableConstraints.anchor = GridBagConstraints.PAGE_START;
        tableConstraints.fill = GridBagConstraints.BOTH;
        tableConstraints.weightx = 1.0;
        tableConstraints.weighty = 1.0;
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
        statsConstraints.fill = GridBagConstraints.BOTH;
        statsConstraints.weightx = 1.0;
        statsConstraints.weighty = 1.0;
        statsConstraints.gridy = 1;
        statsConstraints.gridx = 2;

        this.add(this.table, tableConstraints);

        this.add(this.chart, chartConstraints);

        this.add(this.details, detailsConstraints);

        this.add(this.stats, statsConstraints);


        this.table.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                return;
            }
        });

        this.table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                IrisData data = table.getSelectedItem();
                details.showDetails(data);
                stats.updateAllStats(data);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                return;
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                return;
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                return;
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                return;
            }
        });

        this.stats.addActionListener(actionEvent -> {
            String selectedVariety = stats.getSelectedVariety();

        });
    }

}
