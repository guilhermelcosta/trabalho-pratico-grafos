package entities;

public class Edge {

    private Vertex v;
    private Vertex w;

    /**
     * Construtor padrao de uma aresta
     * @param v aresta V
     * @param w aresta W
     */
    public Edge(Vertex v, Vertex w) {
        this.v = v;
        this.w = w;
    }

    /**
     * Imprime as informacoes da aresta: [v] - [w]
     */
    public void print() {
        System.out.println(v.getId() + " - " + w.getId());
    }

    public Vertex getV() {
        return v;
    }

    public void setV(Vertex v) {
        this.v = v;
    }

    public Vertex getW() {
        return w;
    }

    public void setW(Vertex w) {
        this.w = w;
    }

}
