import java.util.HashMap;
import java.util.Map;

public class App {
    static HashMap<String, Integer> shortest;
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addNodes("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M");
        shortest = graph.shortest;

        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "E", 7);
        graph.addEdge("B", "C", 4);
        graph.addEdge("C", "D", 10);
        graph.addEdge("C", "F", 12);
        graph.addEdge("E", "F", 12);
        graph.addEdge("E", "G", 8);
        graph.addEdge("D", "H", 11);
        graph.addEdge("D", "I", 13);
        graph.addEdge("G", "F", 8);
        graph.addEdge("H", "F", 6);
        graph.addEdge("H", "J", 10);
        graph.addEdge("I", "F", 15);
        graph.addEdge("I", "J", 3);
        graph.addEdge("K", "F", 2);
        graph.addEdge("G", "L", 10);
        graph.addEdge("L", "K", 5);
        graph.addEdge("L", "M", 3);
        graph.addEdge("M", "K", 7);
        graph.addEdge("K", "J", 14);
        graph.addEdge("J", "M", 10);

        System.out.println(dijkstra(graph, "A", "M"));
    }

    public static String dijkstra(Graph graph, String start, String end) {
        PriorityQueue pq = new PriorityQueue();
        pq.insert(start, 0);
        shortest.put(start, 0);
        String path = ""; boolean found = false;

        while (!pq.isEmpty()) {
            HashMap<String, Integer> hm = pq.get();
            String k = (String) hm.keySet().toArray()[0];
            int w = (Integer) hm.values().toArray()[0];

            if (shortest.get(String.valueOf(k.charAt(k.length() - 1))) < w) continue;
            else if (end.equals(String.valueOf(k.charAt(k.length() - 1)))) {
                path += k + ": " + w + "\n";
                found = true;
            }

            hm = graph.adjacentNodes(String.valueOf(k.charAt(k.length() - 1)));
            for (Map.Entry<String, Integer> pair: hm.entrySet()) {
                String node = pair.getKey();
                int weight = pair.getValue() + w;

                if (weight <= shortest.get(node)) {
                    pq.insert(k + node, weight);
                    shortest.put(node, weight);
                }
            }
        }

        if (found) return path;
        else return "No path was found";
    }
}
