package model.dto;

public class FavoriDto extends Dto<String>{
    private String nom;
    private int origine;
    private int destination;
    private String origineName;
    private String destinationName;

    public FavoriDto(String nom,int origine, int destination, String origineName, String destinationName){
        super(nom);
        this.nom = nom;
        this.origine = origine;
        this.destination = destination;
        this.origineName = origineName;
        this.destinationName = destinationName;
    }

    public FavoriDto(String nom, StationDto origine, StationDto destination){
        super(nom);
        this.nom = nom;
        this.origine = origine.getKey();
        this.destination = destination.getKey();
        this.origineName = origine.getNom();
        this.destinationName = destination.getNom();
    }

    public String getNom() {
        return nom;
    }

    public int getOrigine() {
        return origine;
    }

    public int getDestination() {
        return destination;
    }

    public String getOrigineName() {
        return origineName;
    }

    public String getDestinationName() {
        return destinationName;
    }
}
