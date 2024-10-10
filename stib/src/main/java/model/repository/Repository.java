package model.repository;

import model.dto.Dto;
import model.exception.RepositoryException;

import java.util.Map;

public interface Repository<K, T extends Dto<K>> {
    public T get(K key) throws RepositoryException;
    public Map getAll() throws RepositoryException;
}
