package edu.asu.agupt385.cse564.assignment4;

import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

public class StatusLogger extends JPanel {

    private static volatile StatusLogger INSTANCE;

    private JLabel statusLabel;

    private StatusLogger() {
        setLayout(new BorderLayout(1,1));

        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusLabel.setForeground(Color.BLACK);
        add(BorderLayout.CENTER, statusLabel);
    }

    public static StatusLogger getInstance() {
        if (INSTANCE == null) {
            synchronized (StatusLogger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StatusLogger();
                }
            }
        }
        return INSTANCE;
    }

    public void setMessage(String message) {
        if (message.equals(""))
            statusLabel.setText("Ready");
        else
            statusLabel.setText(message);
    }
}
