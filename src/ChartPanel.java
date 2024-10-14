import javax.swing.*;
import java.util.stream.Stream;

public class ChartPanel extends JPanel {

    private Stream<DataCarrier> dataStream;

    ChartPanel(Stream<DataCarrier> dataStream) {
        super();
        this.dataStream = dataStream;
    }

    public void setDataStream(Stream<DataCarrier> dataStream) {
        this.dataStream = dataStream;
    }
}
