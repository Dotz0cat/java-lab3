import javax.swing.*;
import java.util.stream.Stream;

public class ChartPanel extends JPanel {

    private Stream<IrisData> dataStream;

    ChartPanel(Stream<IrisData> dataStream) {
        super();
        this.dataStream = dataStream;
    }

    public void setDataStream(Stream<IrisData> dataStream) {
        this.dataStream = dataStream;
    }
}
