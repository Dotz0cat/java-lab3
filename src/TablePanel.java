import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class TablePanel extends JPanel {
    private final Set<IrisData> dataSet;
    private final TableModel modelTable;
    private JScrollPane pane;
    private JTable table;
    private JPanel checkboxPanel;
    private ArrayList<JCheckBox> checkBoxes;

    //action listener
    private ActionListener actionListener;
    //private MouseListener mouseListener;

    TablePanel(Set<IrisData> dataSet) {
        super(new BorderLayout());

        this.dataSet = dataSet;
        this.checkBoxes = new ArrayList<JCheckBox>();
        this.checkboxPanel = new JPanel();

        for (int i = 0; i < IrisData.dataNames.length; i++) {
            JCheckBox box = new JCheckBox(IrisData.dataNames[i]);
            final int finalI = i;

            box.addActionListener(actionEvent -> {
                TableModel model = table.getModel();
                if (model instanceof ModelTable) {
                    ((ModelTable) model).updateColumnsShown(finalI, box.isSelected());
                }
                dispatchAction();
            });

            box.setSelected(true);
            checkBoxes.add(box);
            checkboxPanel.add(box);
        }

        this.modelTable = new ModelTable();
        this.table = new JTable(this.modelTable);

        this.pane = new JScrollPane(table);

        this.add(this.checkboxPanel, BorderLayout.PAGE_START);

        this.add(this.pane, BorderLayout.CENTER);

    }

    public IrisData getSelectedItem() {
        int row = this.table.getSelectedRow();
        if (modelTable instanceof ModelTable) {
            return ((ModelTable) modelTable).getSelectedItem(row);
        }
        else {
            return null;
        }
    }

    public void filterTable(Predicate<? super IrisData> filter) {
        if (modelTable instanceof ModelTable) {
            ((ModelTable) modelTable).filterData(filter);
        }
    }

    public String[] getShownColumnNames() {
        String[] columnNamesShown = new String[table.getColumnCount()];

        for (int i = 0; i < table.getColumnCount(); i++) {
            columnNamesShown[i] = table.getColumnName(i);
        }

        return columnNamesShown;
    }

    private class ModelTable extends AbstractTableModel {
        private BitSet columnsShown;
        private final IrisData[] dataArray;
        private List<IrisData> displayData;

        ModelTable() {
            super();

            dataArray = dataSet.toArray(new IrisData[0]);
            displayData = List.of(dataArray);
            columnsShown = new BitSet(IrisData.dataNames.length);
            columnsShown.flip(0, IrisData.dataNames.length);
        }

        @Override
        public int getRowCount() {
            return displayData.size();
        }

        @Override
        public int getColumnCount() {
            return columnsShown.cardinality();
        }

        @Override
        public String getColumnName(int columnIndex) {
            int actualIndex = getActualIndex(columnIndex);
            return columnsShown.get(actualIndex) ? IrisData.dataNames[actualIndex] : null;
        }

        private int getActualIndex(int columnIndex) {
            // Java Enums are the worst implementations of enums I have seen.
            //This problem that I had been working on for who knows how many hours could
            // be solved in a few lines of c style enums.
            int actualIndex = -1; // To track the actual index of the shown column
            int count = 0; // To count the number of shown columns

            // Iterate through the columns and count the shown columns
            for (int i = 0; i < IrisData.dataNames.length; i++) {
                if (columnsShown.get(i)) {
                    if (count == columnIndex) {
                        actualIndex = i; // Found the actual index
                        break; // Exit the loop early
                    }
                    count++; // Increment the count of shown columns
                }
            }

            return actualIndex; // Return the found index or -1 if not found
        }

        @Override
        public Object getValueAt(int i, int i1) {
            int actualIndex = getActualIndex(i1);

            if (!columnsShown.get(actualIndex)) {
                return null;
            }
            else {
                IrisData entry = displayData.get(i);
                return switch (actualIndex) {
                    case 0 -> entry.sepalLength();
                    case 1 -> entry.sepalWidth();
                    case 2 -> entry.petalLength();
                    case 3 -> entry.petalWidth();
                    case 4 -> entry.variety();
                    default -> null;
                };
            }
        }

        public void updateColumnsShown(int index, boolean shown) {
            columnsShown.set(index, shown);
            fireTableStructureChanged();
        }

        public IrisData getSelectedItem(int row) {
            return displayData.get(row);
        }

        public void filterData(Predicate<? super IrisData> filter) {
            displayData = List.of(dataArray).stream()
                    .filter(filter)
                    .toList();
            fireTableDataChanged();
        }
    }

    //feels like I should implement an interface for this
    public void addActionListener(ActionListener l) {
        this.actionListener = l;
    }

    public void addMouseListener(MouseListener m) {
        //this.mouseListener = m;
        this.table.addMouseListener(m);
    }

    private void dispatchAction() {
        if (this.actionListener == null) return;

        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");

        this.actionListener.actionPerformed(event);
    }
}
