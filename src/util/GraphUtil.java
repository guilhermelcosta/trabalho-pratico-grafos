package util;

import entities.Edge;
import entities.Graph;
import entities.Vertex;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class GraphUtil {

    /**
     * Armazena as informacoes de um grafo em um arquivo .txt.
     *
     * @param graph grafo a ser armazenado.
     * @throws IOException lanca excecao caso o caminho para armazenar o grafo nao seja encontrado.
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
     * Realiza busca em largura no grafo (BFS).
     * O metodo de BFS foi adaptado a partir do algoritmo disponibilizado pelo prof. Zenilton no material da disciplina.
     *
     * @param graph grafo de referencia.
     * @return booleano indicando se o grafo e ou nao conexo.
     */
    public static boolean isConnected(Graph graph) {

        for (int i = 0; i < graph.getVertices().size(); i++) {
            Set<Vertex> visited = new HashSet<>();
            Queue<Vertex> queue = new LinkedList<>();
            Vertex startVertex = graph.getVertices().get(i);

            queue.offer(startVertex);
            visited.add(startVertex);

            while (!queue.isEmpty()) {
                Vertex currentVertex = queue.poll();

                for (Edge edge : currentVertex.getAdjEdges()) {
                    Vertex neighbor = edge.other(currentVertex);

                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
            if (visited.size() == graph.getVertices().size())
                return true;
        }
        return false;
    }

    /**
     * Implementacao do metodo de Fleury utilizando abordagem naive para identificacao de pontes.
     * O metodo de Fleury foi adaptado a partir do algoritmo disponibilizado pelo prof. Zenilton no material da disciplina.
     *
     * @param graph grafo de referencia.
     * @return booleano indicando se existe ou nao um ciclo euleriano.
     * @throws Exception lanca excecao caso nao seja possivel remover corretamente a aresta do grafo.
     */
    public static boolean fleuryNaive(Graph graph, boolean showPath) throws Exception {

        int verticesWithOddDegree = (int) graph.getVertices().stream()
                .filter(vertex -> vertex.getDegree() % 2 != 0)
                .count();

        if (verticesWithOddDegree > 2) {
            System.out.println("Numero de vertices com grau impar e maior do que 2 -> Nao existe caminho euleriano");
            return false;
        }
        List<Integer> visited = new ArrayList<>();
        Graph graphAux = Graph.copy(graph);
        Vertex v = graphAux.getVertices().get(0);
//        Seleciona vertice inicial com grau impar, se tiver. Caso contrario, inicia do primeiro vertice.
        for (Vertex vertex : graphAux.getVertices()) {
            if (vertex.getDegree() % 2 != 0) {
                v = vertex;
                break;
            }
        }
        visited.add(v.getId());

        while (!graphAux.getEdges().isEmpty()) {
            if (v.getDegree() > 1) {
//                Procura, dentre as arestas adjacentes ao vertice atual, a primeira que nao e ponte.
//                Como os valores de vertices e edges sao passados por referencia, foi criado um novo grafo temporario 'graphTemp',
//                de modo que as alteracoes realizadas nele nao alterem, necessariamente, o grafo auxiliar 'graphAux'.
                for (Edge edge : v.getAdjEdges()) {
                    Graph graphTemp = Graph.copy(graphAux);
                    Edge currentEdge =  graphTemp.getEdges()
                            .stream()
                            .filter(e -> (e.getV().getId() == edge.getV().getId() && e.getW().getId() == edge.getW().getId()))
                            .findFirst()
                            .orElse(null);
                    graphTemp.removeEdge(Objects.requireNonNull(currentEdge));
                    graphTemp.setVertices(graphTemp.getVertices()
                            .stream()
                            .filter(vertex -> vertex.getDegree() > 0)
                            .toList());
//                    Verifica se aresta removida e ou nao uma ponte pelo metodo naive. Caso o grafo, apos a remocao da aresta,
//                    nao seja mais conexo (GraphUtil.isConnected() == false), entao a aresta e uma ponte.
//                    Para essa abordagem, deve-se considerar apenas as arestas com grau > 0. Isso e necessario, pois
//                    caso o grafo ja esteja desconexo (possua arestas com grau = 0), nenhuma aresta mais seria considerada ponte.
//                    Por isso foi utilizado o graphTemp.setVertices() acima para manter apenas os vertices que ainda podem ser explorados (grau > 0).
                    if (GraphUtil.isConnected(graphTemp)) {
                        graphAux.removeEdge(edge);
                        v = edge.other(v);
                        visited.add(v.getId());
                        break;
                    }
                }
            } else {
//                Caso o vertice analisado nao tenha nenhuma aresta, que nao uma ponte,
//                entao e removida a aresta de ponte.
                Edge edge = v.getAdjEdges().get(0);
                v = edge.other(v);
                visited.add(v.getId());
                graphAux.removeEdge(edge);
            }
        }
        if (showPath)
            System.out.println(Arrays.toString(visited.toArray()));
        return Objects.equals(visited.get(0), visited.get(visited.size() - 1));
    }
}
