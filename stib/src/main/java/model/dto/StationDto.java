package model.dto;

import model.Line;

public class StationDto extends Dto<Integer> {
    private String nom;
    private String nomNl;
    private Line line;

    public StationDto(int id,String nom, Line line, String nomNl){
        super(id);
        this.nom = nom;
        this.line = line;
        this.nomNl = nomNl;
    }

    public String getNom(){
        return nom;
    }

    public Line getLine(){
        return line;
    }

    public String getNomNl() {
        return nomNl;
    }
}
