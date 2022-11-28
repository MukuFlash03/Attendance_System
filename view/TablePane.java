package view;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import java.util.List;
import java.util.ArrayList;

public class TablePane {
    private static volatile TablePane INSTANCE;

    private JScrollPane jsp;
    private DefaultTableModel model;
    private JTable dataTable;
    private TableColumnModel colModel;

    private List<String> colNames = new ArrayList<String>();

    private TablePane() {
        model = new DefaultTableModel();
        dataTable = new JTable(model);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        colNames.add("ID");
        colNames.add("First Name");
        colNames.add("Last Name");
        colNames.add("ASURITE");

        for (String col : colNames)
            model.addColumn(col);

        jsp = new JScrollPane(this.dataTable,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
    }

    public static TablePane getInstance() {
        if (INSTANCE == null) {
            synchronized (TablePane.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TablePane();
                }
            }
        }
        return INSTANCE;
    }

    public JTable getJTable() {
        return dataTable;
    }

    public DefaultTableModel getTableModel() {
        return model;
    }

    public JScrollPane getJSP() {
        return jsp;
    }


    public void setJTableColumnsWidth() {
        colModel = dataTable.getColumnModel();
        for (int i = 0; i < colModel.getColumnCount(); i++) {
            TableColumn column = colModel.getColumn(i);
            column.setPreferredWidth(300);
        }
    }

    public void getColumns() {
        for (int i = 0; i < colModel.getColumnCount(); i++)
            System.out.println(colModel.getColumn(i).getHeaderValue());
    }

    public boolean hasColumn(String colName) {
        for (int i = 0; i < colModel.getColumnCount(); i++)
            if (colName.equals(colModel.getColumn(i).getHeaderValue()))
                return true;
        return false;
    }
}
