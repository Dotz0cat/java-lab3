import javax.swing.*;
import java.util.stream.Stream;

public class StatsPanel extends JPanel {
    private Stream<IrisData> dataStream;

    //stats to show
    //Mean
    //Median
    //stdev
    //count
    //z score

    StatsPanel(Stream<IrisData> dataStream) {
        super();

        this.dataStream = dataStream;
    }

    public void setDataStream(Stream<IrisData> dataStream) {
        this.dataStream = dataStream;
    }
}
