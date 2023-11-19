import entities.Graph;
import entities.GraphGenerator;
import util.GraphUtil;

public class Main {
    public static void main(String[] args) throws Exception {

        Graph graph01 = GraphGenerator.eulerian(100000);
        Graph graph02 = GraphGenerator.semiEulerian(100000);
        Graph graph03 = GraphGenerator.nonEulerian(100000);
        Graph graph04 = GraphGenerator.bridgeConnected(100000);

        GraphUtil.saveAsTxt(graph01);
        GraphUtil.saveAsTxt(graph02);
        GraphUtil.saveAsTxt(graph03);
        GraphUtil.saveAsTxt(graph04);

        GraphUtil.fleuryNaive(graph01, false);
        GraphUtil.fleuryTarjan(graph01,false);
        GraphUtil.fleuryNaive(graph02, false);
        GraphUtil.fleuryTarjan(graph02,false);
        GraphUtil.fleuryNaive(graph03, false);
        GraphUtil.fleuryTarjan(graph03,false);
        GraphUtil.fleuryNaive(graph04, false);
        GraphUtil.fleuryTarjan(graph04,false);

    }
}
