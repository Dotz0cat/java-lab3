import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TablePanel extends JPanel {
    private final Stream<IrisData> dataStream;
    private JScrollPane pane;
    private JTable table;
    private JPanel checkboxPanel;
    private ArrayList<JCheckBox> checkBoxes;

    TablePanel(Stream<IrisData> dataStream) {
        super();

        this.dataStream = dataStream;
        this.checkBoxes = new ArrayList<JCheckBox>();

        this.table = new JTable(new ModelTable());

        this.pane = new JScrollPane(table);

        this.add(pane);



    }

    private class ModelTable extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            return null;
        }
    }
}
