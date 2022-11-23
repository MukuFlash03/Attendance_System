package edu.asu.agupt385.cse564.assignment4;

import java.util.Objects;

public class Edge {
    private Vertex source;
    private Vertex destination;
    private RelationshipType relationshipType;

    public Edge(Vertex source, Vertex destination,
                RelationshipType relationshipType) {
        this.source = source;
        this.destination = destination;
        this.relationshipType = relationshipType;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return source.equals(edge.source) && destination.equals(edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
