package view;

import model.Attendance;
import model.GraphDataSource;
import model.Student;
import view.TablePane;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observer;
import java.util.Observable;
import java.util.List;

public class UMLPanel extends JPanel implements Observer {


    private static final int PREF_W = 700;
    private static final int PREF_H = 250;

    private TablePane tableData;

    public UMLPanel() {
        this.setBackground(Color.white);
        this.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));

        this.tableData = TablePane.getInstance();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GraphDataSource) {
            List<Student> studs = ((GraphDataSource) o).getStudents();
            List<Attendance> attendances = ((GraphDataSource) o).getAttendances();

            DefaultTableModel model = tableData.getTableModel();

            String action = ((GraphDataSource) o).getAction();

            tableData.setJTableColumnsWidth();

            if (action.equals("Load")) {
                studs.forEach(stud -> {
                model.addRow(new Object[]{stud.getId(), stud.getFirstName(), stud.getLastName(), stud.getAsurite()});
                });
            }

            if (action.equals("Add")) {
                attendances.forEach(attend -> {
                    String colDate = attend.getFormattedDate();
                    if (!tableData.hasColumn(colDate))
                        model.addColumn(attend.getFormattedDate(), attend.getMinutes());
                });
            }

            tableData.setJTableColumnsWidth();
            // tableData.getJTable().setAutoCreateColumnsFromModel(false);

            tableData.getJSP().setPreferredSize(this.getSize());
            this.add(tableData.getJSP());
        }
    }
}
