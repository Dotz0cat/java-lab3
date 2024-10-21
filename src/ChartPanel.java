import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.util.Set;
import java.util.stream.Stream;

public class ChartPanel extends JPanel {

    private final Set<IrisData> dataSet;

    private JFreeChart chart;

    ChartPanel(Set<IrisData> dataSet) {
        super();
        this.dataSet = dataSet;

        //chart = new JFreeChart("Iris", );
    }



}
