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
     * @throws Exception lanca excecao caso nao seja possivel atualizar o grau do vertice atual.
     */
    private void addAdjVertice(Integer vertexId) throws Exception {
        this.adjVertices.add(vertexId);
        updateDegree();
    }

    /**
     * Remove um vertice adjacente ao vertice atual.
     *
     * @param vertexId id do vertice a ser removido.
     * @throws Exception lanca excecao caso nao seja possivel atualizar o grau do vertice atual.
     */
    private void removeAdjVertice(Integer vertexId) throws Exception {
        this.adjVertices.remove(vertexId);
        updateDegree();
    }

    /**
     * Adiciona uma nova aresta adjacente ao vertice atual.
     *
     * @param edge nova aresta adjacente.
     * @throws Exception lanca excecao caso nao seja possivel atualizar o grau do vertice atual.
     */
    public void addAdjEdge(Edge edge) throws Exception {
        this.adjEdges.add(edge);
        Integer vertexId = edge.getW().getId() == this.id ? edge.getV().getId() : edge.getW().getId();
        addAdjVertice(vertexId);
        updateDegree();
    }

    /**
     * Remove uma aresta adjacente ao vertice atual.
     *
     * @param edge aresta adjacente a ser removida.
     * @throws Exception lanca excecao caso nao seja possivel atualizar o grau do vertice atual.
     */
    public void removeAdjEdge(Edge edge) throws Exception {
        this.adjEdges.remove(edge);
        Integer vertexId = edge.getW().getId() == this.id ? edge.getV().getId() : edge.getW().getId();
        removeAdjVertice(vertexId);
        updateDegree();
    }

    /**
     * Atualiza o grau do vertice atual.
     *
     * @throws Exception lanca excecao caso nao seja possivel atualizar o grau do vertice atual.
     */
    private void updateDegree() throws Exception {
        try {
            if (this.adjVertices.size() == this.adjEdges.size())
                this.degree = this.adjVertices.size();
        } catch (Exception e) {
            throw new Exception("Erro na atualizacao do grau do vertice");
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
