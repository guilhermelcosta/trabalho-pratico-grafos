package util;

import entities.Edge;
import entities.Graph;
import entities.Vertex;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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

    /**
     * Realiza busca em largura no grafo
     *
     * @param graph grafo de referencia
     * @return booleano indicando se o grafo e ou nao conexo
     */
    public static boolean isConnected(Graph graph) {

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        List<Edge> edges = new ArrayList<>(graph.getEdges());

        for (int i = 0; i < vertices.size(); i++) {
            Set<Vertex> visited = new HashSet<>();
            Queue<Vertex> queue = new LinkedList<>();
            Vertex startVertex = vertices.get(i);

            queue.offer(startVertex);
            visited.add(startVertex);

            while (!queue.isEmpty()) {
                Vertex currentVertex = queue.poll();
                /*todo: otimizar esse codigo, sem que ele itere sobre todas as arestas
                 * pode criar um especie de apontador, ou quebrar o loop quando o id de .getV() for maior que o currentVertex.getId()
                 */
                for (Edge edge : edges) {
                    if (edge.getV().getId() > currentVertex.getId())
                        break;

                    if (edge.getV().equals(currentVertex)) {
                        Vertex neighbor = edge.getW();

                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.offer(neighbor);
                        }
                    }
                }
            }
            if (visited.size() == vertices.size())
                return true;
        }
        return false;
    }

}
