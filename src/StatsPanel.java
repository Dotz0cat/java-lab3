import javax.swing.*;
import java.util.stream.Stream;

public class StatsPanel extends JPanel {
    private Stream<DataCarrier> dataStream;

    //stats to show
    //Mean
    //Median
    //stdev
    //count
    //z score

    StatsPanel(Stream<DataCarrier> dataStream) {
        super();

        this.dataStream = dataStream;
    }

    public void setDataStream(Stream<DataCarrier> dataStream) {
        this.dataStream = dataStream;
    }
}
