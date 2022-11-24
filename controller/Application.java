package controller;

import view.ApplicationGUI;

import javax.swing.SwingUtilities;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ApplicationGUI("Assignment 4");
        });
    }
}
