import entities.Graph;
import entities.GraphGenerator;
import util.GraphUtil;

public class Main {
    public static void main(String[] args) throws Exception {

        /*
         * Parametros para gerar de grafos:
         * grafos eulerianos, semi-eulerianos ou nao-eulerianos
         * numero de vertices como 100, 1.000, 10.000 ou 100.000
         */

        Graph graph = GraphGenerator.semiEulerian(10);
        GraphUtil.saveAsTxt(graph);
        System.out.println(GraphUtil.isConnected(graph));

    }
}
