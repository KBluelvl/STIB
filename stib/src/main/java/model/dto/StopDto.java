package model.dto;

import model.Edges;

public class StopDto extends Dto<String>{
    private Edges edges;
    private int id;

    public StopDto(Edges edges,int id,String name){
        super(name);
        this.edges = edges;
        this.id = id;
    }

    public Edges getEdges() {
        return edges;
    }

    public int getId() {
        return id;
    }
}
