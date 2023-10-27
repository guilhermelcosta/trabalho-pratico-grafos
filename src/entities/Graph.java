package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Graph {

    private List<Vertex> vertices;
    private List<Edge> edges;
    private String type;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.type = null;
    }

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.type = null;
    }

    public void print() {
        edges.sort(Comparator.comparingInt(edge -> edge.getV().getId()));
        edges.forEach(Edge::print);
    }

    public void addEdge(Edge edge) {
        edge.getV().addAdj(edge.getW().getId());
        edge.getW().addAdj(edge.getV().getId());
        this.edges.add(edge);
    }

    public void removeEdge(int pos) {
        Edge edgeToRemove = edges.get(pos);
        Vertex v = edgeToRemove.getV();
        Vertex w = edgeToRemove.getW();
        v.removeAdj(w.getId());
        w.removeAdj(v.getId());
        edges.remove(pos);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
