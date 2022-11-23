package edu.asu.agupt385.cse564.assignment4;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.stream.Collectors;

public class SourceCodePanel extends JPanel implements Observer {

    private JTextArea sourceCodeTextArea;

    SourceCodePanel() {
        this.sourceCodeTextArea = new JTextArea(50,50);
        this.add(new JScrollPane(this.sourceCodeTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
                 ));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GraphDataSource) {
            StringBuilder sourceCode = new StringBuilder();
            Set<Vertex> vertices = ((GraphDataSource) o).getAllVertices();
            vertices.forEach(vertex -> {
                Set<Edge> edges = ((GraphDataSource) o).getEdges(vertex);
                sourceCode.append(this.getFormattedVertex(vertex, edges)).append(System.lineSeparator());
            });
            this.sourceCodeTextArea.setText(sourceCode.toString());
        }
    }

    private StringBuilder getFormattedVertex(Vertex vertex, Set<Edge> edges) {
        List<String> classDeclarationRelations = new ArrayList<>();
        List<String> instanceScopeRelations = new ArrayList<>();
        List<String> localScopeRelations = new ArrayList<>();
        StringBuilder classSourceCode = new StringBuilder();

        edges.forEach(edge -> {
            if (edge.getRelationshipType() == RelationshipType.ASSOCIATION) {
                localScopeRelations.add(edge.getDestination().getName());
            } else if (edge.getRelationshipType() == RelationshipType.COMPOSITION) {
                instanceScopeRelations.add(edge.getDestination().getName());
            } else if (edge.getRelationshipType() == RelationshipType.INHERITANCE) {
                classDeclarationRelations.add(edge.getDestination().getName());
            }
        });

        classSourceCode.append("class " + vertex.getName() + " extends ");
        classSourceCode.append(classDeclarationRelations.stream().collect(Collectors.joining(",")));
        classSourceCode.append(" {").append(System.lineSeparator());
        classSourceCode.append(instanceScopeRelations.stream().collect(Collectors.joining(System.lineSeparator())));
        classSourceCode.append(System.lineSeparator());
        classSourceCode.append("method() {").append(System.lineSeparator());
        classSourceCode.append(localScopeRelations.stream().collect(Collectors.joining(System.lineSeparator())));
        classSourceCode.append("}").append(System.lineSeparator());
        classSourceCode.append("}").append(System.lineSeparator());

        return classSourceCode;
    }
}
