package view;

import model.GraphDataSource;
import model.ParseRoster;
import model.Student;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Vector;

public class ApplicationGUI extends JFrame implements ActionListener {

    StatusLogger statusBar;
    MenuPanel menu;
    UMLPanel rightPanel;
    GraphDataSource blackboard;
    TablePane tableData;
    DefaultTableModel model;
    JMenuItem[] items;

    public ApplicationGUI(String title) {
        super(title);
        // setLayout(new BorderLayout());
        // setResizable(false);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) screenSize.getWidth() - ((int) (0.1 * screenSize.getWidth())), (int) screenSize.getHeight() - ((int) (0.1 * screenSize.getHeight())));
        int x = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.getContentPane().setBackground(Color.WHITE);
        setVisible(true);

        // tableData = TablePane.getInstance();
        // tableData.getJSP().setPreferredSize(screenSize);

        initialize();
    }

    private void initialize() {

        blackboard = GraphDataSource.getInstance();
        tableData = TablePane.getInstance();
        rightPanel = new UMLPanel();
        blackboard.addObserver(rightPanel);
        statusBar = StatusLogger.getInstance();
        menu = new MenuPanel();
        setJMenuBar(menu.getMenuBar());
        addComponentsToPane(this.getContentPane(), rightPanel);
        items = menu.getMenuOptions();
        for (JMenuItem item : items)
            item.addActionListener(this);
        
        if (blackboard.getStudents().isEmpty())
            items[1].setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        statusBar.setMessage(choice);
        
        if (e.getSource() == items[0])
            handleLoadRoster();
        // else if (e.getSource() == items[1])
        else if (e.getSource() == items[2])
            handleSaveData();
        else if (e.getSource() == items[4])
            handleTeamInfo();
    }


    public void addComponentsToPane(Container pane, UMLPanel rightPanel) {
 
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 700;
        pane.add(rightPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        pane.add(statusBar, c);
    }

    public void handleTeamInfo() {
        JPanel dPane = new JPanel();
        dPane.setLayout(new GridLayout(0,1));
        JDialog d = new JDialog(this, "Project Team");

        for (String person : menu.getTeamInfo()) {
            JLabel label = new JLabel(person);
            dPane.add(label);
        }
        d.add(dPane);
        d.setSize(500, 500);
        d.setVisible(true);
    }

    public void handleLoadRoster() {
        blackboard.clearRoster();
        model = tableData.getTableModel();
        model.setRowCount(0);

        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        int r = fc.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            File rosterFile = fc.getSelectedFile();
            statusBar.setMessage("Roster File loaded");
            ParseRoster parser = new ParseRoster();
            parser.parseRosterFile(rosterFile);
            if (!blackboard.getStudents().isEmpty())
                items[1].setEnabled(true);
        }
        else {
            statusBar.setMessage("User cancelled the operation");
            if (!blackboard.getStudents().isEmpty())
                items[1].setEnabled(true);
            else if (blackboard.getStudents().isEmpty())
                items[1].setEnabled(false);
        }
    }

    public void handleSaveData() {

        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        int r = fc.showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {

            File rosterFile = fc.getSelectedFile();
            rosterFile = new File(fc.getSelectedFile() + ".csv");
            statusBar.setMessage("Table data saved successfully");

            String DELIMITER = ",";
            String SEPARATOR = "\n";
            String HEADER = "";
    
            model = tableData.getTableModel();
    
            int numberRows = model.getRowCount();
            int numberColumns = model.getColumnCount();
            String[] headers = new String[numberColumns];
    
            for (int i = 0; i < numberColumns; i++)
                headers[i] =  model.getColumnName(i);
    
            HEADER = String.join(DELIMITER + " ", headers);
            
          
            try
            {
                FileWriter saveFileWrite = new FileWriter(rosterFile);
                saveFileWrite.append(HEADER);
                saveFileWrite.append(SEPARATOR);

                Vector allData = model.getDataVector();
                for (int i = 0; i < numberRows; i++) {
                    Vector<String> row = (Vector<String>)allData.elementAt(i);
                    String rowData = String.join(DELIMITER + " ", row);
                    saveFileWrite.append(rowData);

                    saveFileWrite.append(SEPARATOR);
                }
                saveFileWrite.close();
    
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else
            statusBar.setMessage("User cancelled the operation");
    }
}

