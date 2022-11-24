package view;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class TablePane {
    private static volatile TablePane INSTANCE;

    private JScrollPane jsp;
    private DefaultTableModel model;
    private JTable dataTable;
    private String[] colNames = {"ID", "First Name", "Last Name", "ASURITE"};

    private TablePane() {
        model = new DefaultTableModel();
        dataTable = new JTable(model);

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

    public DefaultTableModel getTableModel() {
        return model;
    }

    public JScrollPane getJSP() {
        return jsp;
    }
}
