package model;

import model.dto.StationDto;
import model.exception.RepositoryException;
import model.repository.StopRepository;
import model.util.Observable;
import model.util.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Model {
    private Graph graph;
    private StopRepository stopRepository;
    public Model() throws RepositoryException {
        stopRepository = new StopRepository();
        graph = new Graph(stopRepository.getAll());
    }

    public synchronized List<String> search(StationDto origine, StationDto destionation) throws RepositoryException {
        Optional<Node> matchingNode = graph.getNodes().stream()
                .filter(node -> origine.getNom().equals(node.getName())).findFirst();
        Node nodeOrigine = matchingNode.get();
        Dijkstra.calculateShortestPathFromSource(nodeOrigine);
        matchingNode = graph.getNodes().stream()
                .filter(node -> destionation.getNom().equals(node.getName())).findFirst();
        Node nodeDestination = matchingNode.get();
        List<Node> shortestPath = nodeDestination.getShortestPath();
        List<String> stringList = new ArrayList<>();
        for(Node n: shortestPath){
            stringList.add(n.getName());
        }
        stringList.add(nodeDestination.getName());
        try {
            graph = new Graph(stopRepository.getAll());
        } catch (Exception e){
            throw new RepositoryException(e);
        }
        return stringList;
    }
}
