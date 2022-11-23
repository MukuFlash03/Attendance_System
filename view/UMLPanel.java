package edu.asu.agupt385.cse564.assignment4;


import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;

public class UMLPanel extends JPanel implements MouseInputListener {

    private static final int MAXCOUNT = 5;
    private Vertex currentBox;

    private static final int PREF_W = 700;
    private static final int PREF_H = 250;


    private static final int BOX_W = 150;
    private static final int BOX_H = 75;

    private GraphDataSource blackboard;

    private int boxCount = 0;

    public UMLPanel() {
        blackboard = GraphDataSource.getInstance();
        this.setBackground(Color.white);
        this.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blackboard != null) {
            for (Vertex v : blackboard.getAllVertices())
                paintRectangle(g, v);
        }
    }

    public void paintRectangle(Graphics g, Vertex v) {
        int x = v.getX();
        int y = v.getY();
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, BOX_W, BOX_H);
        g.setColor(Color.BLACK);
        g.drawString(v.getName(), (int)(v.center().getX() - BOX_W/8), (int)(v.center().getY() - BOX_H/8));
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }


    public void mouseClicked(MouseEvent e) {
    }


    public String inputBoxName() {
        String name = (String)JOptionPane.showInputDialog(
                    this,
                    "Enter box name:\n",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");
        
    
        /* Implement null text validation
        if ((s != null) && (s.length() > 0)) {
            System.out.println("Box Name: " + s);
            return s;
        }
        */

        /*
        Need to handle duplicate box name,
        Map key doesn't give error
        BoxCount increments
        Actual drawn boxes reduces
        */
        return name;
    }


    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
		int y = e.getY();
        if (getBox(x, y) != null)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
  		else
			setCursor(Cursor.getDefaultCursor()); 
    }

    public void mouseExited(MouseEvent e) {
    // controller.resetLabel();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        currentBox = getBox(x, y);
        if (currentBox == null) // Point not within existing Box
            addBox(x, y);
    }

    public void mouseDragged(MouseEvent e) {
        
        int x = e.getX(); 
		int y = e.getY();
  		if (currentBox != null) { 
            Graphics graphics = getGraphics(); 
            graphics.setXORMode(getBackground());
            paintRectangle(graphics, currentBox);
            blackboard.updateVertex(currentBox, x, y);
            currentBox.move(x,y);
            paintRectangle(graphics, currentBox);
            graphics.dispose();
            repaint();
  		}        
    }


    public Vertex getBox(int x, int y) {
        for (Vertex v : blackboard.getAllVertices()) {
            if (v.contains(x,y))
                return v;
        }
        return null;
   }

   public void addBox(int x, int y) { 
    
    Vertex newBox;
    String boxName = "";

    if (boxCount < MAXCOUNT ) {
        boxName = inputBoxName();
        newBox = new Vertex(boxName, x, y, 16);
        blackboard.addVertex(newBox);
        currentBox = newBox;
        boxCount = blackboard.getVertexCount();
    }
    else {
        System.out.println("Exceeeded maximum allowed boxes!");
        JOptionPane.showMessageDialog(
            this, 
            "Exceeeded maximum allowed boxes",
            "Warning",
            JOptionPane.WARNING_MESSAGE
        );
    }
    repaint();
    
   }
}
