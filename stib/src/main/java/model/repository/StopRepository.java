package model.repository;

import model.dao.StopDao;
import model.dto.StopDto;
import model.exception.RepositoryException;
import java.util.Map;

public class StopRepository implements Repository<String, StopDto>{
    StopDao stopDao;

    public StopRepository(){
        stopDao = new StopDao();
    }

    public StopRepository(StopDao stopDao){
        this.stopDao = stopDao;
    }

    @Override
    public StopDto get(String key) throws RepositoryException {
        return stopDao.get(key);
    }

    @Override
    public Map getAll() throws RepositoryException {
        return stopDao.getAll();
    }
}
