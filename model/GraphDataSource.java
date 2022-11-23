package edu.asu.agupt385.cse564.assignment4;

import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class GraphDataSource extends Observable {
    private static volatile GraphDataSource INSTANCE;

    private final Map<Vertex, Set<Edge>> graph;

    private GraphDataSource() {
        this.graph = new ConcurrentHashMap<>();
    }

    public static GraphDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (GraphDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GraphDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public void addVertex(Vertex v) {
        graph.put(v, new ConcurrentSkipListSet<>());
        this.setChanged();
        this.notifyObservers();
    }

    public void addEdge(Edge e) {
        graph.get(e.getSource()).add(e);
        this.setChanged();
        this.notifyObservers();
    }

    public boolean hasVertex(Vertex v) {
        return graph.containsKey(v);
    }

    public boolean hasEdge(Edge e) {
        return graph.get(e.getSource()).contains(e);
    }

    public Set<Vertex> getAllVertices() {
        return new HashSet<>(graph.keySet());
    }

    public Set<Edge> getEdges(Vertex v) {
        return new HashSet<>(graph.get(v));
    }

    public Set<Edge> getAllEdges() {
        return graph.values().stream().flatMap(adjacentEdges -> adjacentEdges.stream()).collect(Collectors.toSet());
    }

    public int getVertexCount() {
        return graph.size();
    }

    public void updateVertex(Vertex v, int x, int y) {
        Set<Edge> boxEdges = getEdges(v);
        graph.remove(v);
        v.move(x,y);
        graph.put(v, boxEdges);
    }
}
