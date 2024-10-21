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
        this.add(new JLabel("Sepal Length: " + irisData.sepalLength(), JLabel.CENTER));
        this.add(new JLabel("Sepal Width: " + irisData.sepalWidth(), JLabel.CENTER));
        this.add(new JLabel("Petal Length: " + irisData.petalLength(), JLabel.CENTER));
        this.add(new JLabel("Petal Width: " + irisData.petalWidth(), JLabel.CENTER));
        this.add(new JLabel("Variety: " + irisData.variety(), JLabel.CENTER));

        this.repaint();
        this.revalidate();
    }

    public void clear() {
        this.removeAll();

        this.repaint();
        this.revalidate();
    }

}
