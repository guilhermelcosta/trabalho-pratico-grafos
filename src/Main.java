import entities.Graph;
import entities.GraphGenerator;
import util.GraphUtil;

public class Main {
    public static void main(String[] args) throws Exception {

        /*
         Parametros para gerar de grafos:
         grafos eulerianos, semi-eulerianos ou nao-eulerianos
         numero de vertices como 100, 1.000, 10.000 ou 100.000
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
