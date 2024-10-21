import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.stream.Stream;

public class DetailsPanel extends JPanel {

    DetailsPanel() {
        super(new GridLayout(0, 1));
    }

    public void showDetails(IrisData irisData) {
        if (irisData == null) return;
        this.removeAll();
        this.add(new JLabel("Sepal Length: " + irisData.sepalLength()));
        this.add(new JLabel("Sepal Width: " + irisData.sepalWidth()));
        this.add(new JLabel("Petal Length: " + irisData.petalLength()));
        this.add(new JLabel("Petal Width: " + irisData.petalWidth()));
        this.add(new JLabel("Variety: " + irisData.variety()));

        this.repaint();
        this.revalidate();
    }

}
