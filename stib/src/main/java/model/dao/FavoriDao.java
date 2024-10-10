package model.dao;

import config.ConfigManager;
import model.dto.FavoriDto;
import model.exception.RepositoryException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FavoriDao implements Dao<String, FavoriDto> {
    private String url;
    private Map<String,FavoriDto> favoris = new HashMap<>();
    public FavoriDao() {
        try {
            ConfigManager.getInstance().load();
            this.url = ConfigManager.getInstance().getProperties("db.url");
        } catch (Exception e){
            System.out.println("Database not found !");
        }
    }

    public void insert(FavoriDto favoriDto) {
        try {
            Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            String query = "INSERT INTO FAVORIS(name,id_origine,id_destination) " +
                    "VALUES( ? , ? , ? )";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, favoriDto.getNom());
            stmt.setInt(2, favoriDto.getOrigine());
            stmt.setInt(3, favoriDto.getDestination());
            int count = stmt.executeUpdate();
            System.out.println("\t Nombre de record modifié : " + count);
        } catch (Exception e){
            System.out.println("Cannot insert favori. Error : "+e.getMessage());
        }
    }

    @Override
    public FavoriDto get(String key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException();
        }
        getAll();
        if(favoris.containsKey(key)){
            return favoris.get(key);
        }
        return null;
    }

    @Override
    public Map getAll() throws RepositoryException {
        try {
            Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement stmt = connexion.createStatement();
            String query = "select fav.name, id_origine, origine.name origine, " +
                    "id_destination, destination.name destination from FAVORIS fav " +
                    "JOIN STATIONS origine ON origine.id = fav.id_origine " +
                    "JOIN STATIONS destination ON destination.id = fav.id_destination;";
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                String name = resultSet.getString("name");
                int idOrigine = resultSet.getInt("id_origine");
                int idDestination = resultSet.getInt("id_destination");
                String origine = resultSet.getString("origine");
                String destination = resultSet.getString("destination");
                favoris.put(name, new FavoriDto(name,idOrigine,idDestination,origine,destination));
            }
        } catch (Exception e){
            throw new RepositoryException(e);
        }
        return favoris;
    }

    public void delete(FavoriDto favoriDto){
        try {
        Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + url);
        String query = "DELETE FROM FAVORIS WHERE name = ?";
        PreparedStatement statement = connexion.prepareStatement(query);
        statement.setString(1, favoriDto.getNom());
        int count = statement.executeUpdate();
        System.out.println("\t Nombre de record modifié : " + count);
        } catch (Exception e){
            System.out.println("Cannot delete favori. Error : "+e.getMessage());
        }
    }
}
