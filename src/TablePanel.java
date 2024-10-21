import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class TablePanel extends JPanel {
    private final Set<IrisData> dataSet;
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

        this.table = new JTable(new ModelTable());

        this.pane = new JScrollPane(table);

        this.add(this.checkboxPanel, BorderLayout.PAGE_START);

        this.add(this.pane, BorderLayout.CENTER);

    }

    public IrisData getSelectedItem() {
        int row = this.table.getSelectedRow();
        TableModel model = this.table.getModel();
        if (model instanceof ModelTable) {
            return ((ModelTable) model).getSelectedItem(row);
        }
        else {
            return null;
        }
    }

    private class ModelTable extends AbstractTableModel {
        private int rowCount;
        private BitSet columnsShown;
        private final IrisData[] dataArray;

        ModelTable() {
            super();

            dataArray = dataSet.toArray(new IrisData[0]);
            columnsShown = new BitSet(IrisData.dataNames.length);
            rowCount = dataArray.length;
            columnsShown.flip(0, IrisData.dataNames.length);
        }

        @Override
        public int getRowCount() {
            return rowCount;
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
                IrisData entry = dataArray[i];
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
            return dataArray[row];
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
