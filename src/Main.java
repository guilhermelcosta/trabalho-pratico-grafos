import entities.Graph;
import entities.GraphGenerator;
import util.GraphUtil;

public class Main {
    public static void main(String[] args) throws Exception {

        /*
         1 - Parametros para gerar de grafos:
          - Grafos eulerianos, semi-eulerianos ou nao-eulerianos
          - Numero de vertices como 100, 1.000, 10.000 ou 100.000

         2 - Para testar os grafos, usar a classe GraphGenerator. Exemplos com 100.000 vertices:
          - GraphGenerator.eulerian(100000)
          - GraphGenerator.semiEulerian(100000)
          - GraphGenerator.nonEulerian(100000)
         */

        Graph graph = GraphGenerator.semiEulerian(100000);
        long start = System.currentTimeMillis();
        boolean hasEulerianCycle = GraphUtil.fleuryNaive(graph, false);
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;

        if (!graph.getType().equals("Non-Eulerian")) {
            if (hasEulerianCycle)
                System.out.println("Existe ciclo euleriano");
            else
                System.out.println("Existe trajeto euleriano");
        }
        System.out.println("Tempo de execucao: " + (elapsedTime >= 1000 ? elapsedTime / 1000 + "s" : elapsedTime + "ms"));

    }
}
