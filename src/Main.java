import entities.Graph;
import entities.GraphGenerator;

public class Main {
    public static void main(String[] args) {

        Graph graph = GraphGenerator.semiEulerian(3);
        graph.print();

    }
}
