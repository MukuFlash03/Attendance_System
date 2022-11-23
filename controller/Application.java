package edu.asu.agupt385.cse564.assignment4;

import javax.swing.SwingUtilities;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ApplicationGUI("Assignment 4");
        });
    }
}
