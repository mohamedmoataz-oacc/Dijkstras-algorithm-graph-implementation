import java.util.HashMap;

public class Graph {
    HashMap<String, HashMap<String, Integer>> graph;
    HashMap<String, Integer> shortest = new HashMap<>();
    Graph() {graph = new HashMap<>();}

    public void addNodes(String... nodes) {for (String c: nodes) {addNode(c);}}

    public void addNode(String node) {
        graph.put(node, new HashMap<>());
        shortest.put(node, Integer.MAX_VALUE);
    }

    public void addEdge(String v1, String v2, int weight) {
        graph.get(v1).put(v2, weight);
        graph.get(v2).put(v1, weight);
    }

    public HashMap<String, Integer> adjacentNodes(String node) {return graph.get(node);}

    @Override
    public String toString() {return graph.toString();}
}
