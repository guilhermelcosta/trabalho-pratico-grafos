package entities;

import java.util.Arrays;
import java.util.List;

public class GraphGenerator {

    public static Graph eulerian(int v) {

        /*todo: tratar v = 2*/

        if (v < 3)
            throw new IllegalArgumentException("O grafo deve possuir no minimo tres vertice");

        Graph graph = new Graph();
        Vertex[] vertices = new Vertex[v];

//      Criar os vertices do grafo
        for (int i = 0; i < v; i++)
            vertices[i] = new Vertex(i + 1);
//      Atribui os vertices a suas arestas
        for (int i = 0; i < v - 1; i++){
            graph.addEdge(new Edge(vertices[i], vertices[i + 1]));
//            vertices[i].addAdj(vertices[i + 1].getId());
//            vertices[i + 1].addAdj(vertices[i].getId());
        }

        graph.addEdge(new Edge(vertices[v - 1], vertices[0]));
        graph.setVertices(Arrays.asList(vertices));

        return graph;
    }

    public static Graph semiEulerian(int v) {

        /*todo: tratar v = 2*/

        if (v < 2)
            throw new IllegalArgumentException("O grafo semi-euleriano deve possuir no minimo dois vertice");

        Graph graph = GraphGenerator.eulerian(v);

        if (v > 3) {
            List<Vertex> vertices = graph.getVertices();
            Vertex midVertex = vertices.get(graph.getVertices().size() / 2);
            graph.addEdge(new Edge(midVertex, vertices.get(0)));
        } else
            graph.removeEdge(graph.getEdges().size() - 1);

        return graph;
    }

    public static void saveAsTxt() {

    }
}
