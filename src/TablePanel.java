import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TablePanel extends JPanel {
    private Stream<DataCarrier> dataStream;
    private JScrollPane pane;
    private JTable table;
    private ArrayList<JCheckBox> checkBoxes;

    TablePanel(Stream<DataCarrier> dataStream) {
        super();

        this.dataStream = dataStream;
        this.checkBoxes = new ArrayList<JCheckBox>();

        this.table = new JTable(new ModelTable());

        this.pane = new JScrollPane(table);

        this.add(pane);



    }

    public void setDataStream(Stream<DataCarrier> dataStream) {
        this.dataStream = dataStream;
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
