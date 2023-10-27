package entities;

import java.util.Arrays;
import java.util.List;

public class GraphGenerator {

    public static Graph eulerian(int v) {

        if (v < 3)
            throw new IllegalArgumentException("O grafo deve possuir no minimo tres vertices");

        Graph graph = new Graph();
        Vertex[] vertices = new Vertex[v];

//        Criar os vertices do grafo.
        for (int i = 0; i < v; i++)
            vertices[i] = new Vertex(i + 1);
//        Atribui os vertices a suas arestas.
        for (int i = 0; i < v - 1; i++)
            graph.addEdge(new Edge(vertices[i], vertices[i + 1]));

        graph.addEdge(new Edge(vertices[v - 1], vertices[0]));
        graph.setVertices(Arrays.asList(vertices));
        graph.setType("Eulerian");

        return graph;
    }

    public static Graph semiEulerian(int v) {

        Graph graph = GraphGenerator.eulerian(v);

//        Caso o grafo possua mais de 3 vertices, insere uma nova aresta, de modo que 2 vertices
//        tenham grau impar. Caso possua 3 vertices, remove uma aresta.
        if (v > 3) {
            List<Vertex> vertices = graph.getVertices();
            Vertex midVertex = vertices.get(graph.getVertices().size() / 2);
            Vertex firstVertex = vertices.get(0);

            graph.addEdge(new Edge(midVertex, firstVertex));
        } else
            graph.removeEdge(graph.getEdges().size() - 1);

        graph.setType("Semi-Eulerian");

        return graph;
    }

    public static Graph nonEulerian(int v) {

        if (v < 4)
            throw new IllegalArgumentException("O grafo nao-euleriano deve possuir no minimo quatro vertices");

        Graph graph = GraphGenerator.semiEulerian(v);
        List<Vertex> vertices = graph.getVertices();
        Vertex midVertex = vertices.get((graph.getVertices().size() / 2) - 1);
        Vertex lastVertex = vertices.get(vertices.size() - 1);

        graph.addEdge(new Edge(midVertex, lastVertex));
        graph.setType("Non-Eulerian");

        return graph;
    }

}
