import javax.swing.*;

public class DataVisualizer {
    public static void main(String[] argv) {
        JFrame frame = new JFrame("Data Visualizer");
        DataPanel panel = new DataPanel("iris.csv");
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
