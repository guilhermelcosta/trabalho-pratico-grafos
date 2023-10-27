import entities.Graph;
import entities.GraphGenerator;
import util.GraphUtil;

public class Main {
    public static void main(String[] args) throws Exception {

        Graph graph = GraphGenerator.semiEulerian(100);
        GraphUtil.saveAsTxt(graph);

    }
}
