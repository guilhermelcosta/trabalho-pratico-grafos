package entities;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private int id;
    private List<Integer> adj;

    public Vertex(int id) {
        this.id = id;
        this.adj = new ArrayList<>();
    }

    public Vertex(int id, List<Integer> adj) {
        this.id = id;
        this.adj = adj;
    }

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
