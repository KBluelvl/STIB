package model.dao;

import config.ConfigManager;
import model.Line;
import model.dto.StationDto;
import model.exception.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class StationDao implements Dao<Integer, StationDto> {
    private Map<String,StationDto> stations = new HashMap<>();
    private String url;

    public StationDao() {
        try {
            ConfigManager.getInstance().load();
            url = ConfigManager.getInstance().getProperties("db.url");
        } catch (Exception e){
            System.out.println("Database not found !");
        }
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException();
        }
        getAll();
        if(stations.containsKey(key)){
            return stations.get(key);
        }
        return null;
    }

    @Override
    public Map<String,StationDto> getAll() throws RepositoryException {
        try {
            Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement stmt = connexion.createStatement();
            String query = "select id_line,stat.name nameFr,id_station,nl.name nameNl from STOPS " +
                    "join STATIONS stat on id_station = stat.id " +
                    "join STATIONS_NL nl on stat.id = nl.id " +
                    "order by stat.name;";
            ResultSet resultSet = stmt.executeQuery(query);
            int saveId = -1;
            Line lines = new Line();
            while(resultSet.next()){
                int idStation = resultSet.getInt("id_station");
                String name = resultSet.getString("nameFr");
                String nl = resultSet.getString("nameNl");
                int idLine = resultSet.getInt("id_line");
                if(idStation == saveId) {
                    lines.addLine(idLine);
                } else {
                    lines = new Line();
                    lines.addLine(idLine);
                    saveId = idStation;
                }
                stations.put(name, new StationDto(idStation, name, lines, nl));
                }

        } catch (Exception e){
            throw new RepositoryException(e);
        }
        return stations;
    }
}
