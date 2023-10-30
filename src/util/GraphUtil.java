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
     * Realiza busca em largura no grafo.
     *
     * @param graph grafo de referencia.
     * @return booleano indicando se o grafo e ou nao conexo.
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
                 * pode criar um especie de apontador, ou quebrar o loop quando o id de .getV() for maior que o currentVertex.getId().
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

    /**
     * Implementacao do metodo de Fleury utilizando abordagem naive para identificacao de pontes.
     * O metodo de Fleury foi adaptado a partir do algoritmo disponibilizado pelo prof. Zenilton no material da disciplina.
     *
     * @param graph grafo de referencia.
     * @return booleano indicando se existe ou nao um ciclo euleriano.
     * @throws Exception lanca excecao caso nao seja possivel remover corretamente a aresta do grafo.
     */
    public static boolean fleuryNaive(Graph graph) throws Exception {

        int verticesWithOddDegree = (int) graph.getVertices().stream()
                .filter(vertex -> vertex.getDegree() % 2 != 0)
                .count();

        if (verticesWithOddDegree > 3) {
            System.out.println("Numero de vertices com grau impar e maior do que 3 -> Nao existe caminho euleriano");
            return false;
        }
        List<Integer> visited = new ArrayList<>();
        Graph graphAux = Graph.copy(graph);
        Vertex v = graphAux.getVertices().get(0);

        for (Vertex vertex : graphAux.getVertices()) {
            if (vertex.getDegree() % 2 != 0) {
                v = vertex;
                break;
            }
        }
        visited.add(v.getId());

        while (!graphAux.getEdges().isEmpty()) {
            if (v.getDegree() > 1) {
                for (Edge edge : v.getAdjEdges()) {
                    Graph graphTemp = Graph.copy(graphAux);
                    graphTemp.removeEdge(edge);
//                    Verificacao se aresta removida e ou nao uma ponte, caso o grafo, apos a remocao da aresta
//                    nao seja mais conexo (GraphUtil.isConnected() == false), entao a aresta e uma ponte.
                    if (GraphUtil.isConnected(graphTemp)) {
                        graphAux.removeEdge(edge);
                        v = getNextVertex(v, edge);
                        visited.add(v.getId());
                        break;
                    }
                }
            } else {
//                Caso o vertice analisado nao tenha nenhuma aresta, que nao uma ponte,
//                entao e removida a aresta de ponte.
                v = getNextVertex(v, graphAux.getEdges().get(0));
                visited.add(v.getId());
                graphAux.removeEdge(graphAux.getEdges().get(0));
            }
        }
        System.out.println(Arrays.toString(visited.toArray()));
        return Objects.equals(visited.get(0), visited.get(visited.size() - 1));
    }

    /**
     * Retorna o proximo vertice a ser explorado. Como o grafo nao e direcionado, quando uma aresta e analisada,
     * deve-se verificar em qual vertice da arestas estamos, e ir no outro.
     * Por exemplo: aresta(3 - 5), se estamos explorando o 3, o proximo vertice deve ser o 5, e vice-versa.
     *
     * @param v vertice atual.
     * @param e aresta analisada.
     * @return proximo vertice a ser explorado.
     */
    private static Vertex getNextVertex(Vertex v, Edge e) {
        return v == e.getW() ? e.getV() : e.getW();
    }
}
