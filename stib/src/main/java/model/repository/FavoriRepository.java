package model.repository;

import model.dao.FavoriDao;
import model.dto.FavoriDto;
import model.exception.RepositoryException;

import java.util.Map;

public class FavoriRepository implements Repository<String, FavoriDto>{
    private FavoriDao favoriDao;

    public FavoriRepository(){
        favoriDao = new FavoriDao();
    }

    public FavoriRepository(FavoriDao dao){
        favoriDao = dao;
    }

    @Override
    public FavoriDto get(String key) throws RepositoryException {
        return favoriDao.get(key);
    }

    @Override
    public Map getAll() throws RepositoryException {
        return favoriDao.getAll();
    }

    public void add(FavoriDto item) {
        favoriDao.insert(item);
    }

    public void remove(FavoriDto dto){
        favoriDao.delete(dto);
    }
}
