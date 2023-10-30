package entities;

public class Edge {

    private Vertex v;
    private Vertex w;

    /**
     * Construtor padrao de uma aresta.
     *
     * @param v aresta V.
     * @param w aresta W.
     */
    public Edge(Vertex v, Vertex w) {
        this.v = v;
        this.w = w;
    }

    /**
     * Retorna o outro vertice da aresta.
     * Se a aresta 'edge' possui os vertices (V,W), o metodo edge.other(V), retorna W, e vice-versa
     *
     * @param vertex vertice de referencia.
     * @return retorna o outro vertice da aresta.
     */
    public Vertex other(Vertex vertex) {
        return vertex.getId() == this.getV().getId() ? this.getW() : this.getV();
    }

    /**
     * Imprime as informacoes da aresta: [v] - [w].
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
