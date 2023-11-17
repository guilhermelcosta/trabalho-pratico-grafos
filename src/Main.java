import entities.Graph;
import entities.GraphGenerator;
import util.GraphUtil;

public class Main {
    public static void main(String[] args) throws Exception {

        /*
         1) Parametros p
         ara gerar de grafos:
          - Grafos eulerianos, semi-eulerianos ou nao-eulerianos
          - Numero de vertices como 100, 1.000, 10.000 ou 100.000

         2) Para testar os grafos, usar a classe GraphGenerator. Exemplos com 100.000 vertices:
          - GraphGenerator.eulerian(100000)
          - GraphGenerator.semiEulerian(100000)
          - GraphGenerator.nonEulerian(100000)
         */

        Graph graph = GraphGenerator.bridgeConnected(10);
        GraphUtil.findBridges(graph);

        long start = System.currentTimeMillis();
        boolean hasBridges = graph.hasBridges();
        boolean hasEulerianCycle = GraphUtil.fleuryNaive(graph, true);
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;

        if (hasBridges) {
            System.out.println("Arestas de ponte: ");
            graph.printBridges();
        }
//        todo: revisar essa condicao, ela esta errada
        if (!graph.getType().equals("Non-Eulerian") || !graph.getType().equals("Bridge-connected")) {
            if (hasEulerianCycle)
                System.out.println("Existe ciclo euleriano");
            else
                System.out.println("Existe trajeto euleriano");
        }
        System.out.println("Tempo de execucao: " + (elapsedTime >= 1000 ? elapsedTime / 1000 + "s" : elapsedTime + "ms"));

    }
}
