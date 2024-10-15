import javax.swing.*;
import java.util.stream.Stream;

public class DetailsPanel extends JPanel {

    private Stream<IrisData> dataStream;

    DetailsPanel(Stream<IrisData> dataStream) {
        super();
        this.dataStream = dataStream;
    }

    public void setDataStream(Stream<IrisData> dataStream) {
        this.dataStream = dataStream;
    }
}
