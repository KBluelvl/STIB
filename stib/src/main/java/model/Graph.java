package model;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    private Set<Node> nodes = new HashSet<>();

    public Graph(Map<String,Edges> stops){
        for(Map.Entry<String,Edges> e: stops.entrySet()){
            String name = e.getKey();
            Edges edges = e.getValue();
            boolean containsNode = nodes.stream().anyMatch(node -> name.equals(node.getName()));
            if(!containsNode) {
                Node currentNode = new Node(name);
                nodes.add(currentNode);
                initNodes(currentNode, edges.getAdjacents(), stops);
            }else{
                Optional<Node> matchingNode = nodes.stream()
                        .filter(node -> name.equals(node.getName())).findFirst();
                Node node = matchingNode.get();
                initNodes(node,edges.getAdjacents(), stops);
            }
        }
    }

    private void initNodes(Node currentNode, List<String> names, Map<String,Edges> stops){
        for (String str : names) {
            boolean containsNode = nodes.stream().anyMatch(node -> str.equals(node.getName()));
            Node adjacentNode = null;
            if(!containsNode) {
                adjacentNode = new Node(str);
                nodes.add(adjacentNode);
            } else{
                Optional<Node> matchingNode = nodes.stream()
                        .filter(node -> str.equals(node.getName())).findFirst();
                adjacentNode = matchingNode.get();
            }
            currentNode.addDestination(adjacentNode,1);
        }
    }

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
}
