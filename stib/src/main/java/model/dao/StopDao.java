package model.dao;

import config.ConfigManager;
import model.Edges;
import model.dto.StopDto;
import model.exception.RepositoryException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class StopDao implements Dao<String, StopDto> {
    private Map<String,Edges> stops = new HashMap<>();
    private String url;

    public StopDao(){
        try{
            ConfigManager.getInstance().load();
            url = ConfigManager.getInstance().getProperties("db.url");
        } catch (Exception e){
            System.out.println("Database not found !");
        }
    }

    @Override
    public StopDto get(String key) throws RepositoryException {
        return null;
    }

    @Override
    public Map getAll() throws RepositoryException {
        try{
            Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement stmt = connexion.createStatement();
            String query = "SELECT DISTINCT stat2.name previous_station,stat1.name current_station,stat3.name next_station from STOPS s1 " +
                    "left JOIN STATIONS stat1 on stat1.id = s1.id_station " +
                    "left JOIN STOPS s2 on s1.id_line = s2.id_line and s1.id_order-1 = s2.id_order " +
                    "left JOIN STATIONS stat2 on stat2.id = s2.id_station " +
                    "left JOIN STOPS s3 on s1.id_line = s3.id_line and s1.id_order+1 = s3.id_order " +
                    "left JOIN STATIONS stat3 on stat3.id = s3.id_station " +
                    "order by current_station;";
            ResultSet resultSet = stmt.executeQuery(query);
            String save = "";
            Edges edges = new Edges();
            while(resultSet.next()) {
                String previousStation = resultSet.getString("previous_station");
                String currentStation = resultSet.getString("current_station");
                String nextStation = resultSet.getString("next_station");
                if (!save.equals(currentStation)) {
                    save = currentStation;
                    edges = new Edges();
                }
                if (previousStation != null) {
                    edges.addAdjacent(previousStation);
                }
                if (nextStation != null) {
                    edges.addAdjacent(nextStation);
                }
                stops.put(currentStation, edges);
            }
        } catch (Exception e){
            throw new RepositoryException(e);
        }
        return stops;
    }
}
