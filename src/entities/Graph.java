package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Graph {

    private List<Vertex> vertices;
    private List<Edge> edges;
    private String type;

    /**
     * Construtor padrao do grafo
     *
     * @param vertices lista de vertices
     * @param edges    lista de arestas
     */
    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.type = null;
    }

    /**
     * Construtor vazio do grafo
     */
    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.type = null;
    }

    /**
     * Construtor padrao do grafo com tipo
     *
     * @param vertices lista de vertices
     * @param edges    lista de arestas
     */
    public Graph(List<Vertex> vertices, List<Edge> edges, String type) {
        this.vertices = vertices;
        this.edges = edges;
        this.type = type;
    }

    /**
     * Imprime as arestas do grafo: [v] - [w]
     */
    public void print() {
        edges.sort(Comparator.comparingInt(edge -> edge.getV().getId()));
        edges.forEach(Edge::print);
    }

    public Graph copy () {
        return new Graph(this.vertices, this.edges, this.type);
    }

    /**
     * Adiciona uma nova aresta no grafo e, em seguida, atualiza a lista de adjacencia dos vertices relacionados
     *
     * @param edge nova aresta
     */
    public void addEdge(Edge edge) throws Exception {
        edge.getV().addAdjEdge(edge);
        edge.getW().addAdjEdge(edge);
        this.edges.add(edge);
    }

    /**
     * Remove uma aresta no grafo e, em seguida, atualiza a lista de adjacencia dos vertices relacionados
     *
     * @param edge aresta removida
     */
    public void removeEdge(Edge edge) throws Exception {
        edge.getV().removeAdjEdge(edge);
        edge.getW().removeAdjEdge(edge);
        edges.remove(edge);
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
