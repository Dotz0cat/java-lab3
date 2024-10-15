import javax.swing.*;

public class DataVisualizer {
    public static void main(String[] argv) {
        System.out.printf("Graphical display here\n");

        JFrame frame = new JFrame("Data Visualizer");
        DataPanel panel = new DataPanel("iris.csv");
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void loadData(String data) {

    }
}
