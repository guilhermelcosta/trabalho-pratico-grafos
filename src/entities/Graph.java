package entities;

import java.util.*;

public class Graph {

    private List<Vertex> vertices;
    private List<Edge> edges;
    private String type;
    private boolean hasEulerianCycle;
    private boolean hasEulerianPath;

    /**
     * Construtor padrao do grafo com tipo.
     *
     * @param vertices lista de vertices.
     * @param edges    lista de arestas.
     */
    public Graph(List<Vertex> vertices, List<Edge> edges, String type) {
        this.vertices = vertices;
        this.edges = edges;
        this.type = type;
        this.hasEulerianCycle = false;
        this.hasEulerianPath = false;
    }

    /**
     * Construtor padrao do grafo.
     *
     * @param vertices lista de vertices.
     * @param edges    lista de arestas.
     */
    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.type = null;
        this.hasEulerianCycle = false;
        this.hasEulerianPath = false;
    }

    /**
     * Construtor vazio do grafo.
     */
    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.type = null;
        this.hasEulerianCycle = false;
        this.hasEulerianPath = false;
    }

    /**
     * Copia as informacoes do grafo atual para um novo grafo.
     *
     * @param graph grafo de referencia.
     * @return novo grafo.
     */
    public static Graph copy(Graph graph) {

        Graph newGraph = new Graph();
        List<Vertex> clonedVertices = new ArrayList<>();
        Map<Integer, Vertex> vertexMap = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            Vertex clonedVertex = new Vertex(vertex.getId());
            clonedVertices.add(clonedVertex);
            vertexMap.put(vertex.getId(), clonedVertex);
        }

        for (Edge edge : graph.getEdges()) {
            Vertex v = vertexMap.get(edge.getV().getId());
            Vertex w = vertexMap.get(edge.getW().getId());
            newGraph.addEdge(new Edge(v, w));
        }
        newGraph.setVertices(clonedVertices);
        newGraph.setType(graph.getType());

        return newGraph;
    }

    /**
     * Imprime as arestas do grafo: [v] - [w].
     */
    public void print() {
        edges.sort(Comparator.comparingInt(edge -> edge.getV().getId()));
        edges.forEach(Edge::print);
    }

    /**
     * Imprime as arestas que sao ponte do grafo: [v] - [w].
     */
    public void printBridges() {
        edges.sort(Comparator.comparingInt(edge -> edge.getV().getId()));
        edges.forEach(edge -> {
            if (edge.isBridge())
                edge.print();
        });
    }

    /**
     * Adiciona uma nova aresta no grafo e, em seguida, atualiza a lista de adjacencia dos vertices relacionados.
     *
     * @param edge nova aresta.
     */
    public void addEdge(Edge edge) {
        edge.getV().addAdjEdge(edge);
        edge.getW().addAdjEdge(edge);
        this.edges.add(edge);
    }

    /**
     * Remove uma aresta no grafo e, em seguida, atualiza a lista de adjacencia dos vertices relacionados.
     *
     * @param edge aresta removida.
     */
    public void removeEdge(Edge edge) {
        edge.getV().removeAdjEdge(edge);
        edge.getW().removeAdjEdge(edge);
        edges.remove(edge);
    }

    public boolean hasBridges() {
        return this.getEdges().stream().anyMatch(Edge::isBridge);
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

    public boolean hasEulerianCycle() {
        return hasEulerianCycle;
    }

    public void setHasEulerianCycle(boolean hasEulerianCycle) {
        if (hasEulerianCycle)
            this.setHasEulerianPath(true);
        this.hasEulerianCycle = hasEulerianCycle;
    }

    public boolean hasEulerianPath() {
        return hasEulerianPath;
    }

    public void setHasEulerianPath(boolean hasEulerianPath) {
        this.hasEulerianPath = hasEulerianPath;
    }
}
