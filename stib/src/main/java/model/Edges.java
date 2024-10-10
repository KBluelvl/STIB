package model;

import java.util.ArrayList;
import java.util.List;

public class Edges {
    List<String> adjacents = new ArrayList<>();

    public void addAdjacent(String name) {
        adjacents.add(name);
    }

    public List<String> getAdjacents() {
        return adjacents;
    }
}
