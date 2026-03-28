import java.util.*;

public class Graph {
    Map<Integer, List<Integer>>adjList = new HashMap<>();
    boolean isDirected =false;

    void addNode(int id){
        adjList.putIfAbsent(id, new ArrayList<>());
    }
    

}
