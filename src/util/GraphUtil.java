package util;

import entities.Edge;
import entities.Graph;
import entities.Vertex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
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
            PrintWriter printWriter = new PrintWriter("graphs/graph-" + graph.getType().toLowerCase() + "-" + graph.getVertices().size() + ".txt");
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
     * Armazena o log de execucoes.
     *
     * @param graph       grafo de referencia.
     * @param elapsedTime tempo de execucao.
     * @param method      metodo utilizado.
     * @throws IOException lanca excecao caso o caminho para armazenar o grafo nao seja encontrado.
     */
    private static void generateLog(Graph graph, long elapsedTime, String method) throws IOException {
        try {
            String filePath = "logs/exec-log.txt";
            File file = new File(filePath);

            if (file.exists()) {
                try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, true))) {
                    printWriter.println(String.format("%-18s | %-10s | %-10s | %-15s | %-20s | %-20s | %-20s | %-15s | %-25s",
                            graph.getType(),
                            graph.getVertices().size(),
                            graph.getEdges().size(),
                            method,
                            (elapsedTime >= 1000 ? elapsedTime / 1000 + "s" : elapsedTime + "ms"),
                            graph.hasEulerianCycle() ? "Sim" : "Nao",
                            graph.hasEulerianPath() ? "Sim" : "Nao",
                            graph.hasBridges() ? "Sim" : "Nao",
                            LocalTime.now()));
                }
            } else {
                try (PrintWriter printWriter = new PrintWriter(filePath)) {
                    printWriter.println(String.format("%-18s | %-10s | %-10s | %-15s | %-20s | %-20s | %-20s | %-15s | %-25s",
                            "Tipo",
                            "Vertices",
                            "Arestas",
                            "Metodo",
                            "Tempo de execucao",
                            "Ciclo euleriano?",
                            "Trajeto euleriano?",
                            "Possui pontes?",
                            "Execução"));
                    printWriter.println(String.format("%-18s | %-10s | %-10s | %-15s | %-20s | %-20s | %-20s | %-15s | %-25s",
                            graph.getType(),
                            graph.getVertices().size(),
                            graph.getEdges().size(),
                            method,
                            (elapsedTime >= 1000 ? elapsedTime / 1000 + "s" : elapsedTime + "ms"),
                            graph.hasEulerianCycle() ? "Sim" : "Nao",
                            graph.hasEulerianPath() ? "Sim" : "Nao",
                            graph.hasBridges() ? "Sim" : "Nao",
                            LocalTime.now()));
                }
            }
        } catch (IOException e) {
            throw new IOException("Erro ao salvar o arquivo.");
        }
    }

    /**
     * Implementacao do metodo de Fleury utilizando abordagem naive para identificacao de pontes.
     * O metodo de Fleury
     *
     * @param graph grafo de referencia.
     * @return booleano indicando se existe ou nao um ciclo euleriano.
     */
    public static boolean fleuryNaive(Graph graph, boolean showPath) throws IOException {

        long startTime = System.currentTimeMillis();
        int verticesWithOddDegree = (int) graph.getVertices().stream()
                .filter(vertex -> vertex.getDegree() % 2 != 0)
                .count();

        if (verticesWithOddDegree > 2) {
            System.out.println("Numero de vertices com grau impar e maior do que 2 -> Nao existe caminho euleriano");
            GraphUtil.generateLog(graph, System.currentTimeMillis() - startTime, "Fleury naive");
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
                    Edge currentEdge = graphTemp.getEdges()
                            .stream()
                            .filter(e -> (e.getV().getId() == edge.getV().getId() && e.getW().getId() == edge.getW().getId()))
                            .findFirst()
                            .orElse(null);

                    graphTemp.removeEdge(Objects.requireNonNull(currentEdge));
                    graphTemp.setVertices(graphTemp.getVertices()
                            .stream()
                            .filter(vertex -> vertex.getDegree() > 0)
                            .toList());

                    if (GraphUtil.isConnected(graphTemp)) {
                        graphAux.removeEdge(edge);
                        v = edge.other(v);
                        visited.add(v.getId());
                        break;
                    }
                }
            } else {
                Edge edge = v.getAdjEdges().get(0);
                v = edge.other(v);
                visited.add(v.getId());
                graphAux.removeEdge(edge);
            }
        }
        if (showPath)
            System.out.println(Arrays.toString(visited.toArray()));

        if (Objects.equals(visited.get(0), visited.get(visited.size() - 1))) {
            graph.setHasEulerianCycle(true);
        } else
            graph.setHasEulerianPath(true);

        GraphUtil.generateLog(graph, System.currentTimeMillis() - startTime, "Fleury naive");

        return graph.hasEulerianCycle();
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
     * Implementacao do metodo de Fleury utilizando abordagem pelo metodo de Tarjan para identificacao de pontes.
     * O metodo de Fleury foi adaptado a partir do algoritmo disponibilizado pelo prof. Zenilton no material da disciplina.
     * O metodo de Tarjan (1974) foi utilizado em conjunto com busca em profundidade (DPS), as referencias estao no metodo de identificacao de pontes.
     *
     * @param graph grafo de referencia.
     * @return booleano indicando se existe ou nao um ciclo euleriano.
     */
    public static boolean fleuryTarjan(Graph graph, boolean showPath) throws IOException {

        long startTime = System.currentTimeMillis();
        int verticesWithOddDegree = (int) graph.getVertices().stream()
                .filter(vertex -> vertex.getDegree() % 2 != 0)
                .count();

        GraphUtil.findBridges(graph);

        if (verticesWithOddDegree > 2) {
            System.out.println("Numero de vertices com grau impar e maior do que 2 -> Nao existe caminho euleriano");
            GraphUtil.generateLog(graph, System.currentTimeMillis() - startTime, "Fleury tarjan");
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
                    if (!edge.isBridge()) {
                        graphAux.removeEdge(edge);
                        v = edge.other(v);
                        visited.add(v.getId());
                        break;
                    }
                }
            } else {
                Edge edge = v.getAdjEdges().get(0);
                v = edge.other(v);
                visited.add(v.getId());
                graphAux.removeEdge(edge);
            }
        }
        if (showPath)
            System.out.println(Arrays.toString(visited.toArray()));

        if (Objects.equals(visited.get(0), visited.get(visited.size() - 1))) {
            graph.setHasEulerianCycle(true);
        } else
            graph.setHasEulerianPath(true);

        GraphUtil.generateLog(graph, System.currentTimeMillis() - startTime, "Fleury tarjan");

        return graph.hasEulerianCycle();
    }

    /**
     * Encontra todas as pontes em um grafo.
     *
     * @param graph grafo de referencia.
     */
    private static void findBridges(Graph graph) {

        int numberOfVertices = graph.getVertices().size() + 1;
        int time = 0;
        int[] disc = new int[numberOfVertices], low = new int[numberOfVertices];
        boolean[] visited = new boolean[numberOfVertices];
        Vertex[] parent = new Vertex[numberOfVertices];

        for (int i = graph.getVertices().get(0).getId(); i < graph.getVertices().size(); i++) {
            parent[i] = null;
            visited[i] = false;
        }

        for (int i = graph.getVertices().get(0).getId(); i < graph.getVertices().size(); i++)
            if (!visited[i])
                findAllBridgesTarjanIterative(graph.getVertices().get(i), parent, time, visited, disc, low);
    }

    /**
     * Encontra todas as pontes em um grafo utilizando o metodo de Tarjan (1974) em conjunto com busca em profundidade (DPS), com abordagem recursiva.
     * Referencias:
     * A note on finding the bridges of a graph.txt: https://www2.eecs.berkeley.edu/Pubs/TechRpts/1974/ERL-m-427.pdf
     * Bridges in a graph.txt: https://www.geeksforgeeks.org/bridge-in-a-graph/
     * Find Bridges in a graph.txt using Tarjan's Algorithm: https://gist.github.com/SuryaPratapK/2774cb957a27448b485609418e272f2b
     *
     * @param v       vertice.
     * @param parent  array de parentes.
     * @param time    tempo de execucao.
     * @param visited array de vertices visitados.
     * @param disc    array de tempo de descobrimento.
     * @param low     array de menor tempo em vertices adjacentes.
     */
    private static void  findAllBridgesTarjanRecursive(Vertex v, Vertex[] parent, int time, boolean[] visited,
                                                       int[] disc, int[] low) {

        visited[v.getId()] = true;
        disc[v.getId()] = low[v.getId()] = ++time;

        for (Edge edge : v.getAdjEdges()) {
            Vertex w = edge.other(v);

            if (!visited[w.getId()]) {
                parent[w.getId()] = v;
                findAllBridgesTarjanRecursive(w, parent, time, visited, disc, low);
                low[v.getId()] = Math.min(low[v.getId()], low[w.getId()]);

                if (low[w.getId()] > disc[v.getId()])
                    edge.setBridge(true);
            } else if (w != parent[v.getId()])
                low[v.getId()] = Math.min(low[v.getId()], low[w.getId()]);
        }
    }

    /**
     * Encontra todas as pontes em um grafo utilizando o metodo de Tarjan (1974) em conjunto com busca em profundidade (DPS), com abordagem iterativa.
     * Referencias:
     * A note on finding the bridges of a graph.txt: https://www2.eecs.berkeley.edu/Pubs/TechRpts/1974/ERL-m-427.pdf
     * Bridges in a graph.txt: https://www.geeksforgeeks.org/bridge-in-a-graph/
     * Find Bridges in a graph.txt using Tarjan's Algorithm: https://gist.github.com/SuryaPratapK/2774cb957a27448b485609418e272f2b
     *
     * @param start   vertice inicial.
     * @param parent  array de parentes.
     * @param time    tempo de execucao.
     * @param visited array de vertices visitados.
     * @param disc    array de tempo de descobrimento.
     * @param low     array de menor tempo em vertices adjacentes.
     */
    private static void findAllBridgesTarjanIterative(Vertex start, Vertex[] parent, int time, boolean[] visited, int[] disc, int[] low) {

        Stack<Vertex> stack = new Stack<>();
        stack.push(start);
        visited[start.getId()] = true;

        while (!stack.isEmpty()) {
            Vertex v = stack.peek();
            boolean allChildrenVisited = true;

            for (Edge edge : v.getAdjEdges()) {
                Vertex w = edge.other(v);

                if (!visited[w.getId()]) {
                    stack.push(w);
                    visited[w.getId()] = true;
                    parent[w.getId()] = v;
                    disc[w.getId()] = low[w.getId()] = ++time;
                    allChildrenVisited = false;
                    break;
                } else if (w != parent[v.getId()]) {
                    low[v.getId()] = Math.min(low[v.getId()], disc[w.getId()]);
                    if (disc[w.getId()] < disc[v.getId()]) {
                        edge.setBridge(true);
                    }
                }
            }

            if (allChildrenVisited) {
                stack.pop();
                if (!stack.isEmpty()) {
                    Vertex u = stack.peek();
                    low[u.getId()] = Math.min(low[u.getId()], low[v.getId()]);
                }
            }
        }
    }

}
