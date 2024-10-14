import javax.swing.*;
import java.util.stream.Stream;

public class DetailsPanel extends JPanel {

    private Stream<DataCarrier> dataStream;

    DetailsPanel(Stream<DataCarrier> dataStream) {
        super();
        this.dataStream = dataStream;
    }

    public void setDataStream(Stream<DataCarrier> dataStream) {
        this.dataStream = dataStream;
    }
}
