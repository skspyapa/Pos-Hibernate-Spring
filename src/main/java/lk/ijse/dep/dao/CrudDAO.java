package lk.ijse.dep.dao;

import java.util.List;

public interface CrudDAO<T,ID> extends SuperDAO {
    void save(T entity) throws Exception;

    void update(T entity) throws Exception;

    void delete(ID entityId) throws Exception;

    T find(ID entityId) throws Exception;

    List<T> findAll() throws Exception;
}
