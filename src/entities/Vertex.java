package entities;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private int id;
    private List<Integer> adj;

    /**
     * Construtor padrao de um vertice
     *
     * @param id id do vertice
     */
    public Vertex(int id) {
        this.id = id;
        this.adj = new ArrayList<>();
    }

    /**
     * Construtor com lista de adjacencia de um vertice
     *
     * @param id  id do vertice
     * @param adj lista de adjacencia
     */
    public Vertex(int id, List<Integer> adj) {
        this.id = id;
        this.adj = adj;
    }

    /**
     * Imprime a lista de adjacencia de um vertice: '[v]: adj(0) adj(1) adj(2)...'
     */
    public void printAdj() {
        System.out.printf("[%d]: ", id);
        adj.forEach(integer -> System.out.print(integer + " "));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getAdj() {
        return adj;
    }

    public void setAdj(List<Integer> adj) {
        this.adj = adj;
    }

    public void addAdj(Integer vertexId) {
        adj.add(vertexId);
    }

    public void removeAdj(Integer vertexId) {
        adj.remove(vertexId);
    }
}
