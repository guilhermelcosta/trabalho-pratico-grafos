package entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GraphGenerator {

    /**
     * Constroi um grafo euleriano com 'n' vertices.
     *
     * @param n numero de vertices.
     * @return grafo euleriano.
     */
    public static Graph eulerian(int n) {

        if (n < 3)
            throw new IllegalArgumentException("O grafo deve possuir no minimo tres vertices");

        Graph graph = new Graph();
        Vertex[] vertices = new Vertex[n];
//        Criar os vertices do grafo.
        for (int i = 0; i < n; i++)
            vertices[i] = new Vertex(i + 1);
//        Atribui os vertices a suas arestas.
        for (int i = 0; i < n - 1; i++)
            graph.addEdge(new Edge(vertices[i], vertices[i + 1]));

        graph.addEdge(new Edge(vertices[n - 1], vertices[0]));
        graph.setVertices(Arrays.asList(vertices));
        graph.setType("Eulerian");

        return graph;
    }

    /**
     * Constroi um grafo semi-euleriano com 'n' vertices.
     *
     * @param n numero de vertices.
     * @return grafo semi-euleriano.
     */
    public static Graph semiEulerian(int n) {

        Graph graph = GraphGenerator.eulerian(n);
        if (n > 3) {
            List<Vertex> vertices = graph.getVertices();
            Vertex midVertex = vertices.get(graph.getVertices().size() / 2);
            Vertex firstVertex = vertices.get(0);

            graph.addEdge(new Edge(midVertex, firstVertex));
        } else
            graph.removeEdge(graph.getEdges().get(graph.getEdges().size() - 1));

        graph.setType("Semi-Eulerian");

        return graph;
    }

    /**
     * Constroi um grafo nao-euleriano com 'n' vertices.
     *
     * @param n numero de vertices.
     * @return grafo nao-euleriano.
     */
    public static Graph nonEulerian(int n) {

        if (n < 4)
            throw new IllegalArgumentException("O grafo nao-euleriano deve possuir no minimo quatro vertices");

        Graph graph = GraphGenerator.semiEulerian(n);
        List<Vertex> vertices = graph.getVertices();
        Vertex midVertex = vertices.get((graph.getVertices().size() / 2) - 1);
        Vertex lastVertex = vertices.get(vertices.size() - 1);

        graph.addEdge(new Edge(midVertex, lastVertex));
        graph.setType("Non-Eulerian");

        return graph;
    }

    /**
     * Constroi um grafo conexo com pontes com 'n' vertices.
     *
     * @param n numero de vertices.
     * @return grafo conexo com pontes.
     */
    public static Graph bridgeConnected(int n) {

        if (n < 6)
            throw new IllegalArgumentException("O grafo conexo com pontes deve possuir no minimo seis vertices");

        Graph graph = new Graph();
        Random random = new Random();

        graph = switch (random.nextInt(1, 3)) {
            case 0 -> GraphGenerator.eulerian(n);
            case 1 -> GraphGenerator.semiEulerian(n);
            case 2 -> GraphGenerator.nonEulerian(n);
            default -> graph;
        };
        graph.removeEdge(graph.getEdges().get(0));
        graph.setType("Bridge-connected");

        return graph;
    }

}
