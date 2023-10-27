package util;

import entities.Edge;
import entities.Graph;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class GraphUtil {

    /**
     * Armazena as informacoes de um grafo em um arquivo .txt
     *
     * @param graph grafo a ser armazenado
     * @throws IOException lanca excecao caso o caminho para armazenar o grafo nao seja encontrado
     */
    public static void saveAsTxt(Graph graph) throws IOException {
        try {
            PrintWriter printWriter = new PrintWriter("src/graphs/graph-" + graph.getType().toLowerCase() + "-" + graph.getVertices().size() + ".txt");
            List<Edge> edges = graph.getEdges();

            printWriter.println(graph.getVertices().size() + " " + graph.getEdges().size() + "\n");
            edges.sort(Comparator.comparingInt(edge -> edge.getV().getId()));
            edges.forEach(edge -> printWriter.println(edge.getV().getId() + " - " + edge.getW().getId()));
            printWriter.close();
        } catch (IOException e) {
            throw new IOException("Erro ao salvar o arquivo.");
        }
    }

}
