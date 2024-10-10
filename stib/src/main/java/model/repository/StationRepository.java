package model.repository;

import model.dao.StationDao;
import model.dto.StationDto;
import model.exception.RepositoryException;
import java.util.Map;

public class StationRepository implements Repository<Integer, StationDto> {
    StationDao stationDao;

    public StationRepository(){
        stationDao = new StationDao();
    }

    public StationRepository(StationDao stationDao){
        this.stationDao = stationDao;
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return stationDao.get(key);
    }

    @Override
    public Map getAll() throws RepositoryException {
        return stationDao.getAll();
    }
}
