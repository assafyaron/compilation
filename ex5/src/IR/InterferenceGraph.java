package IR;
import TEMP.*;
import java.util.*;

public class InterferenceGraph {

    public final Map<String, Set<String>> adjacencyList;
    public final int K = 10;

    public InterferenceGraph() {
        adjacencyList = new HashMap<>();
    }

    public void buildGraph() {
        
        for (int i = 0; i < TEMP_FACTORY.getInstance().counter+1; i++) {
            adjacencyList.put("temp_"+i, new HashSet<>());
        }

        IRcommand curr = IR.getInstance().controlGraph.tail;
        while (curr != null) {

            Set<String> outSet = curr.out;
            List<String> temps = new ArrayList<>(outSet);

            // Add edges to the graph
            for (int i = 0; i < temps.size(); i++) {
                for (int j = i + 1; j < temps.size(); j++) {
                    addEdge(temps.get(i), temps.get(j));
                }
            }
            curr = curr.prev.isEmpty() ? null : curr.prev.get(0);
        }
    }

    private void addEdge(String t1, String t2) {
        if (t1 == t2) return; // No self-loops
        adjacencyList.get(t1).add(t2);
        adjacencyList.get(t2).add(t1);
    }

    public void printGraph() {
        for (Map.Entry<String, Set<String>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> { ");
            for (String neighbor : entry.getValue()) {
                System.out.print(neighbor + "-");
            }
            System.out.println("}");
        }
    }

    public Map<String, Integer> colorGraph() {

        Stack<String> stack = new Stack<>();
        Map<String, Set<String>> tempGraph = new HashMap<>();
        Map<String, Integer> colors = new HashMap<>();

        // Copy adjacency list
        for (Map.Entry<String, Set<String>> entry : adjacencyList.entrySet()) {
            tempGraph.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }

        // [1] Remove nodes with < K neighbors
        while (!tempGraph.isEmpty()) {

            String nodeToRemove = null;

            for (String node : tempGraph.keySet()) {
                if (tempGraph.get(node).size() < K) {
                    nodeToRemove = node;
                    break;
                }
            }

            if (nodeToRemove == null) {
                System.out.println("Graph is not 10-colorable!");
                return null;
            }

            // Remove node and push it to stack
            stack.push(nodeToRemove);
            for (String neighbor : tempGraph.get(nodeToRemove)) {
                tempGraph.get(neighbor).remove(nodeToRemove);
            }
            tempGraph.remove(nodeToRemove);
        }

        // [2] Assign colors while popping from stack
        while (!stack.isEmpty()) {

            String node = stack.pop();
            Set<Integer> usedColors = new HashSet<>();

            // Check colors used by neighbors
            for (String neighbor : adjacencyList.get(node)) {
                if (colors.containsKey(neighbor)) {
                    usedColors.add(colors.get(neighbor));
                }
            }

            // Assign the smallest available color
            for (int color = 0; color < K; color++) {
                if (!usedColors.contains(color)) {
                    colors.put(node, color);
                    break;
                }
            }
        }

        // Print colors
        for (Map.Entry<String, Integer> entry : colors.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        
        return colors;
    }
}
