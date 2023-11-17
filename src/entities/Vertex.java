package entities;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private int id;
    private int degree;
    private List<Integer> adjVertices;
    private List<Edge> adjEdges;

    /**
     * Construtor padrao de um vertice.
     *
     * @param id id do vertice.
     */
    public Vertex(int id) {
        this.id = id;
        this.degree = 0;
        this.adjVertices = new ArrayList<>();
        this.adjEdges = new ArrayList<>();
    }

    /**
     * Construtor vazio do vertice.
     */
    public Vertex() {
        this.id = Integer.MAX_VALUE;
        this.degree = 0;
        this.adjVertices = new ArrayList<>();
        this.adjEdges = new ArrayList<>();
    }

    /**
     * Imprime a lista de adjacencia de um vertice: '[v]: adj(0) adj(1) adj(2)...'.
     */
    public void printAdjVertices() {
        System.out.printf("[%d]: ", id);
        adjVertices.forEach(integer -> System.out.print(integer + " "));
    }

    /**
     * Imprime a lista de adjacencia de um vertice: [v] - [w].
     */
    public void printAdjEdges() {
        System.out.printf("[%d]: ", id);
        adjEdges.forEach(Edge::print);
    }

    /**
     * Adiciona um novo vertice adjacente ao vertice atual.
     *
     * @param vertexId id do novo vertice.
     */
    private void addAdjVertice(Integer vertexId) {
        this.adjVertices.add(vertexId);
        updateDegree();
    }

    /**
     * Remove um vertice adjacente ao vertice atual.
     *
     * @param vertexId id do vertice a ser removido.
     */
    private void removeAdjVertice(Integer vertexId) {
        this.adjVertices.remove(vertexId);
        updateDegree();
    }

    /**
     * Adiciona uma nova aresta adjacente ao vertice atual.
     *
     * @param edge nova aresta adjacente.
     */
    public void addAdjEdge(Edge edge) {
        this.adjEdges.add(edge);
        Integer vertexId = edge.other(this).getId();
        addAdjVertice(vertexId);
        updateDegree();
    }

    /**
     * Remove uma aresta adjacente ao vertice atual.
     *
     * @param edge aresta adjacente a ser removida.
     */
    public void removeAdjEdge(Edge edge) {
        this.adjEdges.remove(edge);
        Integer vertexId = edge.other(this).getId();
        removeAdjVertice(vertexId);
        updateDegree();
    }

    /**
     * Atualiza o grau do vertice atual.
     */
    private void updateDegree() {
        try {
            if (this.adjVertices.size() == this.adjEdges.size())
                this.degree = this.adjVertices.size();
        } catch (Exception e) {
            System.err.println("Erro na atualização do grau do vértice: " + e.getMessage());
            this.degree = -1;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getAdjVertices() {
        return adjVertices;
    }

    public void setAdjVertices(List<Integer> adjVertices) {
        this.adjVertices = adjVertices;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public List<Edge> getAdjEdges() {
        return adjEdges;
    }

    public void setAdjEdges(List<Edge> adjEdges) {
        this.adjEdges = adjEdges;
    }

}
