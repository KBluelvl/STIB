package model.dao;

import model.exception.RepositoryException;

import java.util.Map;

import model.dto.*;

public interface Dao <K, T extends Dto<K>>{
    public T get(K key) throws RepositoryException;

    public Map getAll() throws RepositoryException;
}
